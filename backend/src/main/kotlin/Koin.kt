import datasource.di.dataSourceModule
import datasource.di.formatter
import datasource.di.logger
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import repository.di.repositoryModule
import usecase.di.useCaseModule

fun Application.initKoin() {
    val supabaseClient = createSupabaseClient(
        supabaseUrl = environment.config.propertyOrNull("ktor.security.supabaseUrl")?.getString()!!,
        supabaseKey = environment.config.propertyOrNull("ktor.security.supabaseKey")?.getString()!!,
    ) {
        defaultSerializer = KotlinXSerializer(formatter)
        install(Postgrest)
    }

    logger.info("SupabaseClient: ${supabaseClient.supabaseUrl}, ${supabaseClient.supabaseKey}")

    install(Koin) {
        slf4jLogger()

        modules(
            module {
                single<SupabaseClient> { supabaseClient }
            },
        )

        modules(dataSourceModule)
        modules(repositoryModule)
        modules(useCaseModule)
    }
}
