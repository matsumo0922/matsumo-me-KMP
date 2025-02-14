import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.initKoin() {
    val supabaseClient = createSupabaseClient(
        supabaseUrl = environment.config.propertyOrNull("ktor.security.supabaseUrl")?.getString()!!,
        supabaseKey = environment.config.propertyOrNull("ktor.security.supabaseKey")?.getString()!!,
    ) {
        install(Postgrest)
    }

    install(Koin) {
        slf4jLogger()

        module {
            single<SupabaseClient> { supabaseClient }
        }
    }
}
