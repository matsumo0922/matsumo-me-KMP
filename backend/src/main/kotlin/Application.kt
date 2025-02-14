
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(Koin) {

    }
    install(ContentNegotiation)
    install(Resources)

    routing {
        get("/") {
            val url = environment.config.propertyOrNull("ktor.security.supabaseUrl")?.getString()
            val key = environment.config.propertyOrNull("ktor.security.supabaseKey")?.getString()

            call.respondText("url: $url\nkey: $key")
        }
        get("/articles") {

        }
    }
}
