import io.github.jan.supabase.SupabaseClient
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.plugin.scope

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation)
    install(Resources)
    install(CallLogging)
    initKoin()

    routing {
        get("/") {
            val client = call.scope.get<SupabaseClient>()
            call.respondText("Hello, world! ${client.supabaseKey}")
        }
        get("/articles") {
        }
    }
}
