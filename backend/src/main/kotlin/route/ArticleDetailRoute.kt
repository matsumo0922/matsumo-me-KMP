package route

import Route
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import usecase.GetArticleDetailEntityUseCase

fun Application.articleDetailRoute() {
    val getArticleDetailEntityUseCase by inject<GetArticleDetailEntityUseCase>()

    routing {
        get<Route.Articles.Article> { article ->
            getArticleDetailEntityUseCase(article.id)?.also {
                call.respond(it)
            } ?: run {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}
