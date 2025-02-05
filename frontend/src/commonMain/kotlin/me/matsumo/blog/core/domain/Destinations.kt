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
            Route("articles") { Articles }
        )

        private fun matchRoute(url: Url, routePattern: String): Map<String, String>? {
            return emptyMap()
        }
    }
}

private data class Route<T : Destinations>(
    val pattern: String,
    val parse: (Map<String, String>) -> T?
)
