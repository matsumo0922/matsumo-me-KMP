package me.matsumo.blog.screen.root

import io.ktor.http.*
import io.ktor.util.*
import me.matsumo.blog.core.utils.log

class ClientRouter {

    private val segments = mutableMapOf<String, (Segment) -> DefaultRootComponent.Navigation>()

    fun get(
        segment: String,
        parser: (Segment) -> DefaultRootComponent.Navigation,
    ) {
        segments[segment] = parser
    }

    fun parse(url: Url): DefaultRootComponent.Navigation? {
        for (segment in segments) {
            log("$url, ${segment.key}")
            (parseSegment(url, segment.key) ?: continue).also {
                return runCatching { segment.value.invoke(it) }.getOrNull()
            }
        }

        return null
    }

    private fun parseSegment(url: Url, patternSegment: String): Segment? {
        val regexPattern = "^" + patternSegment.replace(Regex("\\{\\w+\\}"), "(\\\\w+)") + "$"
        val pattern = Regex(regexPattern)

        val names = Regex("\\{(\\w+)\\}").findAll(patternSegment).map { it.groupValues[1] }.toList()
        val result = pattern.find(url.pathSegments.joinToString("/")) ?: return null

        val pathParameters = names.zip(result.destructured.toList()).toMap()

        return Segment(
            segment = patternSegment,
            pathParameters = pathParameters,
            queryParameters = url.parameters,
        )
    }

    data class Segment(
        val segment: String,
        val pathParameters: Map<String, Any>,
        val queryParameters: Parameters,
    )

    companion object {
        fun routing(builder: ClientRouter.() -> Unit): ClientRouter {
            return ClientRouter().apply(builder)
        }
    }
}
