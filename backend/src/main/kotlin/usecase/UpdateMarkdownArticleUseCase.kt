package usecase

import datasource.di.logger
import domain.MarkdownArticleDetailEntity
import domain.dao.ArticleDao
import domain.dao.MarkdownArticleDetailDao
import me.matsumo.blog.shared.entity.ArticleSource
import repository.ArticleRepository

class UpdateMarkdownArticleUseCase(
    private val articleRepository: ArticleRepository,
) {
    suspend operator fun invoke() {
        articleRepository.getMarkdownArticlesEntity().forEach { listEntity ->
            val entity = articleRepository.getMarkdownArticleDetailEntity(listEntity.path, listEntity.sha)
            val currentArticle = articleRepository.getArticle(sourceId = entity.sha)
            val currentArticleDetail = currentArticle?.id?.let { articleRepository.getArticleDetailFromMarkdown(it) }

            val newArticleDetail = resolveArticleDetail(entity, currentArticleDetail)
            val newArticle = resolveArticle(newArticleDetail, currentArticle)

            saveArticle(newArticle, newArticleDetail)
        }
    }

    private fun resolveArticleDetail(
        entity: MarkdownArticleDetailEntity,
        currentDetail: MarkdownArticleDetailDao?,
    ): MarkdownArticleDetailDao {
        val baseDetail = entity.toMarkdownArticleDetail()
        return currentDetail?.let { baseDetail.copy(id = it.id) } ?: baseDetail
    }

    private fun resolveArticle(
        articleDetail: MarkdownArticleDetailDao,
        currentArticle: ArticleDao?,
    ): ArticleDao {
        val baseArticle = articleDetail.toArticle()
        return currentArticle?.let { baseArticle.copy(id = it.id) } ?: baseArticle
    }

    private suspend fun saveArticle(article: ArticleDao, articleDetail: MarkdownArticleDetailDao) {
        val insertedArticle = articleRepository.upsertArticle(article)

        if (insertedArticle?.id == null) {
            logger.warn("Failed to insert article: ${article.title}")
            return
        }

        val insertedArticleDetail = articleRepository.upsertMarkdownArticleDetail(articleDetail.copy(articleId = insertedArticle.id))

        if (insertedArticleDetail == null) {
            logger.warn("Failed to insert article detail: ${articleDetail.title}")
        } else {
            logger.info("Inserted article: ${insertedArticle.title}")
        }
    }

    private fun MarkdownArticleDetailEntity.toMarkdownArticleDetail(): MarkdownArticleDetailDao {
        return MarkdownArticleDetailDao(
            articleId = 0,
            sourceId = sha,
            sourceUrl = "https://github.com/matsumo0922/matsumo-me-KMP/blob/master/$path",
            title = title,
            content = content,
            renderedContent = content,
            isPublished = true,
            tags = tags,
            createdAt = publishedAt,
            updatedAt = publishedAt,
        )
    }

    private fun MarkdownArticleDetailDao.toArticle(): ArticleDao {
        return ArticleDao(
            sourceId = sourceId,
            source = ArticleSource.MARKDOWN,
            sourceUrl = sourceUrl,
            title = title,
            summary = content?.take(100),
            tags = tags,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
