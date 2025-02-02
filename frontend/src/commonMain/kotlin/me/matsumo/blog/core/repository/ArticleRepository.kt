package me.matsumo.blog.core.repository

import de.jensklingenberg.ktorfit.Ktorfit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.matsumo.blog.core.datastore.ArticleApi
import me.matsumo.blog.core.datastore.ArticleMapper
import me.matsumo.blog.core.datastore.createArticleApi
import me.matsumo.blog.core.domain.model.Article
import me.matsumo.blog.core.domain.model.ArticleDetail

class ArticleRepository(
    private val articleApi: ArticleApi,
    private val articleMapper: ArticleMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun getArticles(): List<Article> = withContext(ioDispatcher){
        articleApi.getArticles().let(articleMapper::map)
    }

    suspend fun getArticleDetail(id: Int): ArticleDetail = withContext(ioDispatcher){
        articleApi.getArticleDetail(id).let(articleMapper::map)
    }
}
