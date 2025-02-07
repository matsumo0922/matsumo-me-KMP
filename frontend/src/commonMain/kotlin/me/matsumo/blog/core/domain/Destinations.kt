package me.matsumo.blog.core.domain

import io.ktor.http.Url
import kotlinx.serialization.Serializable

@Serializable
sealed interface Destinations {

    @Serializable
    data object Home : Destinations

    @Serializable
    data object Articles : Destinations

    companion object {
        private val routes = listOf(
            Route("home") { Home },
            Route("articles") { Articles },
        )

        private fun matchRoute(url: Url, routePattern: String): Map<String, String>? {
            val pathSegments = url.rawSegments.filter { it.isNotBlank() }
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
            val queryParams = url.parameters.entries().associate { it.key to it.value.firstOrNull().orEmpty() }

            for (route in routes) {
                val pathParams = matchRoute(url, route.pattern) ?: continue
                val allParams = queryParams + pathParams
                val destination = route.parse(allParams)

                if (destination != null) return destination
            }

            return null
        }
    }
}

private data class Route<T : Destinations>(
    val pattern: String,
    val parse: (Map<String, String>) -> T?,
)
