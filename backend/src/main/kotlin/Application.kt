import datasource.di.formatter
import io.ktor.resources.Resource
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.resources.Resources
import kotlinx.serialization.Serializable
import route.articleDetailRoute
import route.articlesRoute
import route.ogContentsRoute
import route.revisionRoute
import route.updateArticlesRoute

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(formatter)
    }
    install(Resources)
    install(CallLogging)
    install(CORS) {
        anyHost()
        anyMethod()

        allowCredentials = true
        allowNonSimpleContentTypes = true
    }
    initKoin()
    routes()
}

fun Application.routes() {
    updateArticlesRoute()
    articlesRoute()
    articleDetailRoute()
    ogContentsRoute()
    revisionRoute()
}

@Serializable
sealed interface Route {

    @Serializable
    @Resource("/articles")
    data object Articles : Route {

        @Serializable
        @Resource("{id}")
        data class Article(
            val parent: Articles = Articles,
            val id: Int,
        ) : Route
    }

    @Serializable
    @Resource("/update_articles")
    data object UpdateArticles : Route

    @Serializable
    @Resource("/og_contents")
    data class OgContents(val url: String) : Route

    @Serializable
    @Resource("/revision")
    data object Revision : Route
}
