package me.matsumo.blog.core.datastore.di

import de.jensklingenberg.ktorfit.Ktorfit
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.matsumo.blog.core.datastore.ArticleMapper
import me.matsumo.blog.core.datastore.createArticleApi
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val formatter = Json {
    isLenient = true
    prettyPrint = true
    ignoreUnknownKeys = true
    coerceInputValues = true
    encodeDefaults = true
    explicitNulls = false
}

val datastoreModule = module {
    factory {
        val ktorfit = Ktorfit.Builder()
            .baseUrl("https://matsumo-me-api.fly.dev/api/")
            .httpClient {
                install(Logging) {
                    level = LogLevel.INFO
                    logger = object : Logger {
                        override fun log(message: String) {
                            Napier.d(message)
                        }
                    }
                }

                install(ContentNegotiation) {
                    json(formatter)
                }
            }
            .build()

        ktorfit.createArticleApi()
    }

    factoryOf(::ArticleMapper)
}
