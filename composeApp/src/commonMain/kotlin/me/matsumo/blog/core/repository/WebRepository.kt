package me.matsumo.blog.core.repository

import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.matsumo.blog.core.model.WebMetadata
import me.matsumo.blog.core.model.getIframe
import me.matsumo.blog.core.model.getOG

interface WebRepository  {
    suspend fun getWebMetadata(url: String): WebMetadata
}

class WebRepositoryImpl(
    private val client: ApiClient,
    private val dispatcher: CoroutineDispatcher,
): WebRepository {

    private val metadataCache = mutableMapOf<String, WebMetadata>()

    override suspend fun getWebMetadata(url: String): WebMetadata = withContext(dispatcher) {
        (getIframe(url) ?: getOG(client.get(url).bodyAsText())).also {
            metadataCache[url] = it
        }
    }
}