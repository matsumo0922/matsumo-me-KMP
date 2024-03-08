package me.matsumo.blog.core.repository.di

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import me.matsumo.blog.core.repository.MMRepository
import me.matsumo.blog.core.repository.MMRepositoryImpl
import me.matsumo.blog.core.utils.log
import org.koin.dsl.module

val repositoryModule = module {
    single {
        Json {
            isLenient = true
            prettyPrint = true
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
            explicitNulls = false
        }
    }

    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.INFO
                logger = object : Logger {
                    override fun log(message: String) {
                        log("API", message)
                    }
                }
            }

            install(ContentNegotiation) {
                json(get<Json>())
            }
        }
    }

    single<MMRepository> {
        MMRepositoryImpl(
            client = get(),
            formatter = get(),
            ioDispatcher = get()
        )
    }
}
