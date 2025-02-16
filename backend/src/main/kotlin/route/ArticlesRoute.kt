package route

import Route
import io.ktor.server.application.Application
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import usecase.GetArticleEntitiesUseCase

fun Application.articlesRoute() {
    val getArticleEntitiesUseCase by inject<GetArticleEntitiesUseCase>()

    routing {
        get<Route.Articles> {
            call.respond(getArticleEntitiesUseCase())
        }
    }
}
