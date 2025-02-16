package usecase

import datasource.di.logger
import domain.ZennArticleDetailEntity
import domain.dao.ArticleDao
import domain.dao.ZennArticleDetailDao
import me.matsumo.blog.shared.entity.ArticleSource
import repository.ArticleRepository

class UpdateZennArticleUseCase(
    private val articleRepository: ArticleRepository,
) {
    suspend operator fun invoke() {
        articleRepository.getZennArticlesEntity().articles.forEach { listEntity ->
            val entity = articleRepository.getZennArticleDetailEntity(listEntity.slug).article
            val currentArticle = articleRepository.getArticle(sourceId = entity.slug)
            val currentArticleDetail = currentArticle?.id?.let { articleRepository.getArticleDetailFromZenn(it) }

            val newArticleDetail = resolveArticleDetail(entity, currentArticleDetail)
            val newArticle = resolveArticle(newArticleDetail, currentArticle)

            saveArticle(newArticle, newArticleDetail)
        }
    }

    private fun resolveArticleDetail(
        entity: ZennArticleDetailEntity.Article,
        currentDetail: ZennArticleDetailDao?,
    ): ZennArticleDetailDao {
        val baseDetail = entity.toZennArticleDetail()
        return currentDetail?.let { baseDetail.copy(id = it.id) } ?: baseDetail
    }

    private fun resolveArticle(
        articleDetail: ZennArticleDetailDao,
        currentArticle: ArticleDao?,
    ): ArticleDao {
        val baseArticle = articleDetail.toArticle()
        return currentArticle?.let { baseArticle.copy(id = it.id) } ?: baseArticle
    }

    private suspend fun saveArticle(article: ArticleDao, articleDetail: ZennArticleDetailDao) {
        val insertedArticle = articleRepository.upsertArticle(article)

        if (insertedArticle?.id == null) {
            logger.warn("Failed to insert article: ${article.title}")
            return
        }

        val insertedArticleDetail = articleRepository.upsertZennArticleDetail(articleDetail.copy(articleId = insertedArticle.id))

        if (insertedArticleDetail == null) {
            logger.warn("Failed to insert article detail: ${articleDetail.title}")
        } else {
            logger.info("Inserted article: ${insertedArticle.title}")
        }
    }

    private fun ZennArticleDetailEntity.Article.toZennArticleDetail(): ZennArticleDetailDao {
        return ZennArticleDetailDao(
            articleId = 0,
            sourceId = slug,
            sourceUrl = "https://zenn.dev$path",
            title = title,
            content = bodyHtml,
            renderedContent = bodyHtml,
            isPublished = true,
            emoji = emoji,
            articleType = postType,
            tags = topics.map { it.name },
            createdAt = publishedAt,
            updatedAt = bodyUpdatedAt,
        )
    }

    private fun ZennArticleDetailDao.toArticle(): ArticleDao {
        return ArticleDao(
            sourceId = sourceId,
            source = ArticleSource.ZENN,
            sourceUrl = sourceUrl,
            title = title,
            summary = content?.take(100),
            tags = tags,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
