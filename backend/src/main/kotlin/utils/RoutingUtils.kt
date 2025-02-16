package utils

import datasource.di.formatter
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.RoutingContext

suspend inline fun <reified T : Any> RoutingContext.runCatchingResponse(block: () -> T) = runCatching { block() }
    .onSuccess { call.respondText(formatter.encodeToString(it)) }
    .onFailure { call.respond(HttpStatusCode.InternalServerError) }
