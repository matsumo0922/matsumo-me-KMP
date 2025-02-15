package usecase

import datasource.di.logger
import domain.QiitaArticleEntity
import domain.dao.ArticleDao
import domain.dao.ArticleSource
import domain.dao.QiitaArticleDetail
import repository.ArticleRepository

class UpdateQiitaArticleUseCase(
    private val articleRepository: ArticleRepository,
) {
    suspend operator fun invoke() {
        articleRepository.getQiitaArticlesEntity().forEach { entity ->
            val currentArticle = articleRepository.getArticle(sourceId = entity.id)
            val currentArticleDetail = currentArticle?.id?.let { articleRepository.getArticleDetailFromQiita(it) }

            val newArticleDetail = resolveArticleDetail(entity, currentArticleDetail)
            val newArticle = resolveArticle(newArticleDetail, currentArticle)

            saveArticle(newArticle, newArticleDetail)
        }
    }

    private fun resolveArticleDetail(
        entity: QiitaArticleEntity,
        currentDetail: QiitaArticleDetail?,
    ): QiitaArticleDetail {
        val baseDetail = entity.toQiitaArticleDetail()
        return currentDetail?.let { baseDetail.copy(id = it.id) } ?: baseDetail
    }

    private fun resolveArticle(
        articleDetail: QiitaArticleDetail,
        currentArticle: ArticleDao?,
    ): ArticleDao {
        val baseArticle = articleDetail.toArticle()
        return currentArticle?.let { baseArticle.copy(id = it.id) } ?: baseArticle
    }

    private suspend fun saveArticle(article: ArticleDao, articleDetail: QiitaArticleDetail) {
        val insertedArticle = articleRepository.upsertArticle(article)

        if (insertedArticle?.id == null) {
            logger.warn("Failed to insert article: ${article.title}")
            return
        }

        val insertedArticleDetail = articleRepository.upsertQiitaArticleDetail(articleDetail.copy(articleId = insertedArticle.id))

        if (insertedArticleDetail == null) {
            logger.warn("Failed to insert article detail: ${articleDetail.title}")
        } else {
            logger.info("Inserted article: ${insertedArticle.title}")
        }
    }

    private fun QiitaArticleEntity.toQiitaArticleDetail(): QiitaArticleDetail {
        return QiitaArticleDetail(
            articleId = 0,
            sourceId = id,
            sourceUrl = url,
            title = title,
            content = body,
            renderedContent = renderedBody,
            isPublished = !isPrivate,
            commentsCount = commentsCount,
            likesCount = likesCount,
            reactionsCount = reactionsCount,
            stocksCount = stocksCount,
            tags = tags.map { it.name },
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    private fun QiitaArticleDetail.toArticle(): ArticleDao {
        return ArticleDao(
            sourceId = sourceId,
            source = ArticleSource.QIITA,
            title = title,
            summary = content?.take(100),
            tags = tags,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
