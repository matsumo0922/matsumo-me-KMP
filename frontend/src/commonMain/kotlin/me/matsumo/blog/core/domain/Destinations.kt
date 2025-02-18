package me.matsumo.blog.core.domain

import androidx.core.bundle.Bundle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.serialization.decodeArguments
import io.github.aakira.napier.Napier
import io.ktor.http.Url
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

@Serializable
sealed interface Destinations {

    @Serializable
    data object Home : Destinations

    @Serializable
    data object Articles : Destinations

    @Serializable
    data class ArticleDetail(
        val id: Long,
    ) : Destinations

    @Serializable
    data class PrivacyPolicy(
        val appName: String,
    ) : Destinations

    @Serializable
    data class TermsOfService(
        val appName: String,
    ) : Destinations

    @Serializable
    data object Revision : Destinations

    @Suppress("UNCHECKED_CAST")
    fun toUrlPath(): String {
        val route = routes.firstOrNull { it.type.isInstance(this) }

        requireNotNull(route) { "Route not found for $this" }

        val buildPath = route.buildPath as (Destinations) -> Map<String, String>
        val buildQuery = route.buildQuery as (Destinations) -> Map<String, String>
        val pathParams = buildPath(this)
        val queryParams = buildQuery(this)

        val path = route.pattern.replace(Regex("\\{([^}]+)}")) { matchResult ->
            pathParams[matchResult.groupValues[1]] ?: ""
        }

        return buildString {
            append(path)

            if (queryParams.isNotEmpty()) {
                append("?")
            }

            append(
                queryParams.entries.joinToString("&") { (name, value) ->
                    "$name=$value"
                },
            )
        }
    }

    companion object {
        private val argPlaceholder = "(?:.*\\.)?([^/]+)".toRegex()

        private val routes: List<Route<out Destinations>> = listOf(
            Route(
                pattern = "home",
                type = Home::class,
                parse = { _ -> Home },
            ),
            Route(
                pattern = "articles",
                type = Articles::class,
                parse = { _ -> Articles },
            ),
            Route(
                pattern = "articles/{id}",
                type = ArticleDetail::class,
                parse = { params ->
                    val id = params["id"]?.toLong() ?: return@Route null
                    ArticleDetail(id)
                },
                buildPath = { article -> mapOf("id" to article.id.toString()) },
            ),
            Route(
                pattern = "application/{appName}/privacy_policy",
                type = PrivacyPolicy::class,
                parse = { params ->
                    val appName = params["appName"] ?: return@Route null
                    PrivacyPolicy(appName)
                },
                buildPath = { privacyPolicy -> mapOf("appName" to privacyPolicy.appName) },
            ),
            Route(
                pattern = "application/{appName}/team_of_service",
                type = TermsOfService::class,
                parse = { params ->
                    val appName = params["appName"] ?: return@Route null
                    TermsOfService(appName)
                },
                buildPath = { termsOfService -> mapOf("appName" to termsOfService.appName) },
            ),
            Route(
                pattern = "revision",
                type = Revision::class,
                parse = { _ -> Revision },
            ),
        )

        private fun matchRoute(url: Url, routePattern: String): Map<String, String>? {
            val pathSegments = url.rawSegments.filter { it.isNotBlank() }.toMutableList()
            val patternSegments = routePattern.split("/").filter { it.isNotBlank() }

            if (pathSegments.size != patternSegments.size) return null

            val params = mutableMapOf<String, String>()

            for ((pathSegment, patternSegment) in pathSegments.zip(patternSegments)) {
                if (patternSegment.startsWith("{") && patternSegment.endsWith("}")) {
                    val key = patternSegment.substring(1, patternSegment.length - 1)
                    params[key] = pathSegment
                } else {
                    if (pathSegment != patternSegment) return null
                }
            }

            return params
        }

        fun fromUrl(url: Url): Destinations? {
            Napier.d { "Destinations: fromUrl: $url" }

            val queryParams = url.parameters.entries().associate { it.key to it.value.firstOrNull().orEmpty() }

            for (route in routes) {
                val pathParams = matchRoute(url, route.pattern) ?: continue
                val allParams = queryParams + pathParams
                val destination = route.parse(allParams)

                if (destination != null) return destination
            }

            return null
        }

        @OptIn(InternalSerializationApi::class)
        fun fromBackStackEntry(backStackEntry: NavBackStackEntry): Destinations? {
            val routePackage = backStackEntry.destination.route ?: return null
            val currentRoute = argPlaceholder.find(routePackage)?.groupValues?.get(1) ?: routePackage

            val bundle = backStackEntry.arguments ?: Bundle()
            val typeMap = backStackEntry.destination.arguments.mapValues { it.value.type }

            for (route in routes) {
                runCatching { route.type.serializer().decodeArguments(bundle, typeMap) }.onSuccess {
                    if (it::class.simpleName == currentRoute) return it
                }
            }

            return null
        }
    }
}

private data class Route<T : Destinations>(
    val pattern: String,
    val type: KClass<T>,
    val parse: (Map<String, String>) -> T?,
    val buildPath: (T) -> Map<String, String> = { emptyMap() },
    val buildQuery: (T) -> Map<String, String> = { emptyMap() },
)
