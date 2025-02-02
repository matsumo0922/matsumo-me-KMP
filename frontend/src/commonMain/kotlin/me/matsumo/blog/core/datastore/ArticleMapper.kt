package me.matsumo.blog.core.datastore

import me.matsumo.blog.core.domain.entity.ArticleDetailEntity
import me.matsumo.blog.core.domain.entity.ArticleEntity
import me.matsumo.blog.core.domain.model.Article
import me.matsumo.blog.core.domain.model.ArticleDetail

class ArticleMapper {
    fun map(articles: List<ArticleEntity>): List<Article> {
        return articles.map {
            Article(
                id = it.id,
                title = it.title,
                resource = it.resource,
                tags = it.tags,
                createdAt = it.createdAt,
                publishedAt = it.publishedAt,
                updatedAt = it.updatedAt,
                url = it.url
            )
        }
    }

    fun map(articleDetailEntity: ArticleDetailEntity): ArticleDetail {
        return ArticleDetail(
            id = articleDetailEntity.id,
            title = articleDetailEntity.title,
            body = articleDetailEntity.body,
            tags = articleDetailEntity.tags,
            createdAt = articleDetailEntity.createdAt,
            publishedAt = articleDetailEntity.publishedAt,
            updatedAt = articleDetailEntity.updatedAt,
            url = articleDetailEntity.url
        )
    }
}
