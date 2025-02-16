
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import usecase.UpdateMarkdownArticleUseCase
import usecase.UpdateQiitaArticleUseCase
import usecase.UpdateZennArticleUseCase

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation)
    install(Resources)
    install(CallLogging)
    initKoin()
    routes()
}

fun Application.routes() {
    val updateQiitaArticleUseCase by inject<UpdateQiitaArticleUseCase>()
    val updateZennArticleUseCase by inject<UpdateZennArticleUseCase>()
    val updateMarkdownArticleUseCase by inject<UpdateMarkdownArticleUseCase>()

    routing {
        post("/update_articles") {
            updateQiitaArticleUseCase()
            updateZennArticleUseCase()
            updateMarkdownArticleUseCase()
        }
    }
}
