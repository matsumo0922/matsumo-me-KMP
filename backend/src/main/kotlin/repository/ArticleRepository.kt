package repository

import datasource.ArticleDatabase
import domain.dao.ArticleDao
import domain.dao.MarkdownArticleDetailDao
import domain.dao.QiitaArticleDetailDao
import domain.dao.ZennArticleDetailDao

class ArticleRepository(
    private val articleDatabase: ArticleDatabase,
) {
    suspend fun removeAll(): Boolean = articleDatabase.removeAll()

    suspend fun getArticle(articleId: Int): ArticleDao? = articleDatabase.getArticle(articleId)

    suspend fun getArticle(sourceId: String): ArticleDao? = articleDatabase.getArticle(sourceId)

    suspend fun getArticles(tagId: Int? = null): List<ArticleDao>? = articleDatabase.getArticles(tagId)

    suspend fun getArticleDetailFromMarkdown(articleId: Int): MarkdownArticleDetailDao? = articleDatabase.getArticleDetailFromMarkdown(articleId)

    suspend fun getArticleDetailFromQiita(articleId: Int): QiitaArticleDetailDao? = articleDatabase.getArticleDetailFromQiita(articleId)

    suspend fun getArticleDetailFromZenn(articleId: Int): ZennArticleDetailDao? = articleDatabase.getArticleDetailFromZenn(articleId)

    suspend fun upsertArticle(articleDao: ArticleDao): ArticleDao? = articleDatabase.upsertArticle(articleDao)

    suspend fun upsertMarkdownArticleDetail(markdownArticleDetailDao: MarkdownArticleDetailDao): MarkdownArticleDetailDao? = articleDatabase.upsertMarkdownArticleDetail(markdownArticleDetailDao)

    suspend fun upsertQiitaArticleDetail(qiitaArticleDetail: QiitaArticleDetailDao): QiitaArticleDetailDao? = articleDatabase.upsertQiitaArticleDetail(qiitaArticleDetail)

    suspend fun upsertZennArticleDetail(zennArticleDetailDao: ZennArticleDetailDao): ZennArticleDetailDao? = articleDatabase.upsertZennArticleDetail(zennArticleDetailDao)
}
