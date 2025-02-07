package me.matsumo.blog.core.datastore

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import me.matsumo.blog.core.domain.entity.ArticleDetailEntity
import me.matsumo.blog.core.domain.entity.ArticleEntity

interface ArticleApi {

    @GET("articles")
    suspend fun getArticles(): List<ArticleEntity>

    @GET("articles/markdown/{id}")
    suspend fun getArticleDetail(
        @Path("id") id: Int,
    ): ArticleDetailEntity
}
