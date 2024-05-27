package me.matsumo.blog.core.repository.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import me.matsumo.blog.core.common.extensions.formatter
import kotlinx.serialization.json.Json
import me.matsumo.blog.core.repository.ApiClient
import me.matsumo.blog.core.repository.BlogRepository
import me.matsumo.blog.core.repository.BlogRepositoryImpl
import me.matsumo.blog.core.repository.UserDataRepository
import me.matsumo.blog.core.repository.UserDataRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<Json> {
        formatter
    }

    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.INFO
                logger = object : Logger {
                    override fun log(message: String) {
                        co.touchlab.kermit.Logger.d(message)
                    }
                }
            }

            install(ContentNegotiation) {
                json(get<Json>())
            }

            install(HttpRequestRetry) {
                maxRetries = 3
                exponentialDelay()
            }
        }
    }

    single {
        ApiClient(
            client = get(),
            ioDispatcher = get(),
        )
    }

    single<UserDataRepository> {
        UserDataRepositoryImpl(
            userDataStore = get()
        )
    }

    single<BlogRepository> {
        BlogRepositoryImpl(
            client = get(),
            dispatcher = get()
        )
    }
}
