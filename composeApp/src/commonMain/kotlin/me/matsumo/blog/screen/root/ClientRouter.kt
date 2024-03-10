package me.matsumo.blog.screen.root

import io.ktor.http.*
import io.ktor.util.*

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
            (parseSegment(url, segment.key) ?: continue).also {
                return runCatching { segment.value.invoke(it) }.getOrNull()
            }
        }

        return null
    }

    private fun parseSegment(url: Url, segment: String): Segment? {
        val pattern = segment.replace(Regex("\\{\\w+\\}")) { "(\\w+)" }.toRegex()
        val names = Regex("\\{(\\w+)\\}").findAll(segment).map { it.groupValues[1] }.toList()
        val result = pattern.matchEntire(url.toString()) ?: return null

        return Segment(
            segment = segment,
            pathParameters = names.zip(result.groupValues.drop(1)).toMap(),
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
