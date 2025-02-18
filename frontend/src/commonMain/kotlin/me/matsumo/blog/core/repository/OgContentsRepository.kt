package me.matsumo.blog.core.repository

import io.ktor.http.Url
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.matsumo.blog.core.datastore.OgContentsApi
import me.matsumo.blog.core.datastore.OgContentsMapper
import me.matsumo.blog.core.ui.utils.IO
import me.matsumo.blog.shared.model.OgContents

class OgContentsRepository(
    private val ogContentsApi: OgContentsApi,
    private val ogContentsMapper: OgContentsMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    private val cache = mutableMapOf<String, OgContents>()

    suspend fun getOgContents(url: String): OgContents = withContext(ioDispatcher) {
        require(Url(url).host.lowercase() !in UNSUPPORTED_HOST)

        cache[url] ?: ogContentsApi.getOgContents(url).let(ogContentsMapper::map).also {
            cache[url] = it
        }
    }

    companion object {
        private val UNSUPPORTED_HOST = listOf(
            "x.com",
            "twitter.com",
        )
    }
}
