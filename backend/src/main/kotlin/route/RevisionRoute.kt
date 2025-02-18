package route

import Route
import io.ktor.server.application.Application
import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing

fun Application.revisionRoute() {
    routing {
        get<Route.Revision> {
            call.respondText(environment.config.propertyOrNull("ktor.security.revision")?.getString()!!)
        }
    }
}
