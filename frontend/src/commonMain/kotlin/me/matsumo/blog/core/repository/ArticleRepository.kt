package me.matsumo.blog.core.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.matsumo.blog.core.datastore.ArticleApi
import me.matsumo.blog.core.datastore.ArticleMapper
import me.matsumo.blog.core.ui.utils.IO
import me.matsumo.blog.shared.model.Article
import me.matsumo.blog.shared.model.ArticleDetail

class ArticleRepository(
    private val articleApi: ArticleApi,
    private val articleMapper: ArticleMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun getArticles(): List<Article> = withContext(ioDispatcher) {
        articleApi.getArticles().let(articleMapper::map)
    }

    suspend fun getArticleDetail(id: Long): ArticleDetail = withContext(ioDispatcher) {
        articleApi.getArticleDetail(id).let(articleMapper::map)
    }
}
