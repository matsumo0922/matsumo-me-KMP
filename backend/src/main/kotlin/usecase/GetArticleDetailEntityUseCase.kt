package usecase

import domain.dao.MarkdownArticleDetailDao
import domain.dao.QiitaArticleDetailDao
import domain.dao.ZennArticleDetailDao
import me.matsumo.blog.shared.entity.ArticleDetailEntity
import me.matsumo.blog.shared.entity.ArticleSource
import repository.ArticleRepository

class GetArticleDetailEntityUseCase(
    private val articleRepository: ArticleRepository,
) {
    suspend operator fun invoke(articleId: Int): ArticleDetailEntity? {
        val articles = articleRepository.getArticles() ?: return null
        val article = articles.find { it.id == articleId } ?: return null
        val extra = articles.find { it.title.replace(" ", "") == article.title.replace(" ", "") }

        return when (article.source) {
            ArticleSource.MARKDOWN -> articleRepository.getArticleDetailFromMarkdown(articleId)?.toArticleDetailEntity(extra?.source, extra?.sourceUrl)
            ArticleSource.QIITA -> articleRepository.getArticleDetailFromQiita(articleId)?.toArticleDetailEntity()
            ArticleSource.ZENN -> articleRepository.getArticleDetailFromZenn(articleId)?.toArticleDetailEntity()
        }
    }

    private fun MarkdownArticleDetailDao.toArticleDetailEntity(
        extraSource: ArticleSource? = null,
        extraSourceUrl: String? = null,
    ): ArticleDetailEntity? {
        return id?.let {
            ArticleDetailEntity(
                id = it,
                source = ArticleSource.MARKDOWN,
                articleId = articleId,
                sourceId = sourceId,
                sourceUrl = sourceUrl,
                title = title,
                content = content,
                extraSource = extraSource,
                extraSourceUrl = extraSourceUrl,
                tags = tags,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }

    private fun QiitaArticleDetailDao.toArticleDetailEntity(): ArticleDetailEntity? {
        return id?.let {
            ArticleDetailEntity(
                id = it,
                source = ArticleSource.QIITA,
                articleId = articleId,
                sourceId = sourceId,
                sourceUrl = sourceUrl,
                title = title,
                content = content,
                extraSource = null,
                extraSourceUrl = null,
                tags = tags,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }

    private fun ZennArticleDetailDao.toArticleDetailEntity(): ArticleDetailEntity? {
        return id?.let {
            ArticleDetailEntity(
                id = it, ArticleSource.ZENN,
                articleId = articleId,
                sourceId = sourceId,
                sourceUrl = sourceUrl,
                title = title,
                content = content,
                extraSource = null,
                extraSourceUrl = null,
                tags = tags,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}
