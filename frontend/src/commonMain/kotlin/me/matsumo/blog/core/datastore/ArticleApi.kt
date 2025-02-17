package me.matsumo.blog.core.datastore

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import me.matsumo.blog.shared.entity.ArticleDetailEntity
import me.matsumo.blog.shared.entity.ArticleEntity

interface ArticleApi {

    @GET("articles")
    suspend fun getArticles(): List<ArticleEntity>

    @GET("articles/{id}")
    suspend fun getArticleDetail(
        @Path("id") id: Long,
    ): ArticleDetailEntity
}
