package me.matsumo.blog.core.datastore

import me.matsumo.blog.shared.entity.ArticleDetailEntity
import me.matsumo.blog.shared.entity.ArticleEntity
import me.matsumo.blog.shared.model.Article
import me.matsumo.blog.shared.model.ArticleDetail

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
                url = it.url,
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
            updatedAt = articleDetailEntity.updatedAt,
            url = articleDetailEntity.url,
        )
    }
}
