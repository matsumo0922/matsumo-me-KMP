package route

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import usecase.UpdateMarkdownArticleUseCase
import usecase.UpdateQiitaArticleUseCase
import usecase.UpdateZennArticleUseCase

fun Application.updateArticlesRoute() {
    val updateQiitaArticleUseCase by inject<UpdateQiitaArticleUseCase>()
    val updateZennArticleUseCase by inject<UpdateZennArticleUseCase>()
    val updateMarkdownArticleUseCase by inject<UpdateMarkdownArticleUseCase>()

    routing {
        post("/update_articles") {
            runCatching {
                updateQiitaArticleUseCase()
                updateZennArticleUseCase()
                updateMarkdownArticleUseCase()
            }.onSuccess {
                call.respond(HttpStatusCode.OK)
            }.onFailure {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }
}
