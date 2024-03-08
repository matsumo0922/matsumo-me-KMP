package me.matsumo.blog.core.repository

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import me.matsumo.blog.core.model.Article
import me.matsumo.blog.core.utils.log
import me.matsumo.blog.core.utils.parse

interface MMRepository {

    suspend fun getArticles(): List<Article>
}

class MMRepositoryImpl(
    private val client: HttpClient,
    private val formatter: Json,
    private val ioDispatcher: CoroutineDispatcher,
) : MMRepository {

    override suspend fun getArticles(): List<Article> = withContext(ioDispatcher) {
        log("MMRepositoryImpl", "getArticles")
        get("articles").parse<List<Article>>()!!
    }

    private suspend fun get(dir: String, parameters: Map<String, String> = emptyMap()): HttpResponse {
        return client.get {
            url("$API/$dir")

            for ((key, value) in parameters) {
                parameter(key, value)
            }
        }
    }

    companion object {
        private const val API = "https://matsumo-me-api.fly.dev/api"
    }
}