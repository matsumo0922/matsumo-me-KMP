package me.matsumo.blog.core.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import me.matsumo.blog.core.common.extensions.parse
import me.matsumo.blog.core.model.Article
import me.matsumo.blog.core.model.ArticleDetail

interface BlogRepository {
    suspend fun getArticles(): List<Article>
    suspend fun getArticle(id: String): ArticleDetail
}

class BlogRepositoryImpl(
    private val client: ApiClient,
    private val dispatcher: CoroutineDispatcher,
): BlogRepository {

    override suspend fun getArticles(): List<Article> = withContext(dispatcher) {
        client.get("articles").parse()!!
    }

    override suspend fun getArticle(id: String): ArticleDetail = withContext(dispatcher) {
        client.get("articles/$id").parse()!!
    }
}
