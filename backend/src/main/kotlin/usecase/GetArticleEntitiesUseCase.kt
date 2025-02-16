package usecase

import domain.dao.ArticleDao
import me.matsumo.blog.shared.entity.ArticleEntity
import me.matsumo.blog.shared.entity.ArticleSource
import repository.ArticleRepository

class GetArticleEntitiesUseCase(
    private val articleRepository: ArticleRepository,
) {
    suspend operator fun invoke(): List<ArticleEntity> {
        val articles = articleRepository.getArticles()
        val entities = articles?.mapNotNull { it.toArticleEntity() }
            .orEmpty()
            .distinctBy { it.title.replace(" ", "") }
            .sortedByDescending { it.createdAt }

        return entities
    }

    private fun ArticleDao.toArticleEntity(): ArticleEntity? {
        return id?.let {
            ArticleEntity(
                id = it,
                sourceId = sourceId,
                source = ArticleSource.valueOf(source.name),
                title = title,
                summary = summary,
                tags = tags,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
