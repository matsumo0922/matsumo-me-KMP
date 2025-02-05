package me.matsumo.blog.core.ui.utils

import androidx.core.bundle.Bundle
import androidx.navigation.NavBackStackEntry
import io.ktor.http.Url
import io.ktor.util.toMap
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import me.matsumo.blog.core.domain.Destinations

private val argPlaceholder = "(?:.*\\.)?([^/]+)".toRegex()

fun NavBackStackEntry.toUrlPath(): String? {
    val routePackage = destination.route ?: return null
    val route = argPlaceholder.find(routePackage)?.groupValues?.get(1) ?: routePackage

    val args = arguments ?: Bundle()
    val nameToTypedValue = destination.arguments.mapValues { (name, arg) ->
        arg.type.serializeAsValue(arg.type[args, name])
    }

    return getUrlPath(route, nameToTypedValue)
}

fun Destinations.toUrlPath(): String {
    val json = Json { encodeDefaults = true }
    val element = json.encodeToJsonElement(this)
    val parameters = element.jsonObject.map { it.key to it.value.jsonPrimitive.content }.toMutableList()

    parameters.removeFirst()

    return getUrlPath(
        route = this::class.simpleName.orEmpty(),
        parameters = parameters.toMap(),
    )
}

private fun getUrlPath(
    route: String,
    parameters: Map<String, String>,
): String {
    return StringBuilder(256).apply {
        append(route.lowercase())

        if (parameters.isNotEmpty()) {
            append("?")
        }

        append(
            parameters.entries.joinToString("&") { (name, value) ->
                "$name=$value"
            }
        )
    }.toString()
}
