package me.matsumo.blog.core.repository

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
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getOgContents(url: String): OgContents = withContext(ioDispatcher) {
        ogContentsApi.getOgContents(url).let(ogContentsMapper::map)
    }
}
