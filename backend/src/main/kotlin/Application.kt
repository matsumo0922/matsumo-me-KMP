
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
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
        get("/articles") {

        }
    }
}
