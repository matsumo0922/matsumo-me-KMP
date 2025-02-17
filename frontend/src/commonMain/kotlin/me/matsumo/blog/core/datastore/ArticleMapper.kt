package me.matsumo.blog.core.datastore

import me.matsumo.blog.shared.entity.ArticleDetailEntity
import me.matsumo.blog.shared.entity.ArticleEntity
import me.matsumo.blog.shared.model.Article
import me.matsumo.blog.shared.model.ArticleDetail

class ArticleMapper {
    fun map(articles: List<ArticleEntity>): List<Article> {
        return articles.map {
            Article(
                id = it.id.toLong(),
                sourceId = it.sourceId,
                source = it.source,
                sourceUrl = it.sourceUrl,
                title = it.title,
                summary = it.summary,
                tags = it.tags,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }
    }

    fun map(articleDetailEntity: ArticleDetailEntity): ArticleDetail {
        return ArticleDetail(
            id = articleDetailEntity.id.toLong(),
            source = articleDetailEntity.source,
            articleId = articleDetailEntity.articleId.toLong(),
            sourceId = articleDetailEntity.sourceId,
            sourceUrl = articleDetailEntity.sourceId,
            title = articleDetailEntity.title,
            content = articleDetailEntity.content.orEmpty(),
            tags = articleDetailEntity.tags,
            extraSource = articleDetailEntity.extraSource,
            extraSourceUrl = articleDetailEntity.extraSourceUrl,
            createdAt = articleDetailEntity.createdAt,
            updatedAt = articleDetailEntity.updatedAt
        )
    }
}
