package route

import Route
import io.ktor.server.application.Application
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import usecase.GetOgContentsEntityUseCase

fun Application.ogContentsRoute() {
    val getOgContentsEntityUseCase by inject<GetOgContentsEntityUseCase>()

    routing {
        get<Route.OgContents> {
            call.respond(getOgContentsEntityUseCase(it.url))
        }
    }
}
