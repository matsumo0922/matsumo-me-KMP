package repository

import datasource.ArticleDatabase
import domain.QiitaArticleEntity
import domain.ZennArticleDetailEntity
import domain.ZennArticleEntity
import domain.dao.ArticleDao
import domain.dao.MarkdownArticleDetailDao
import domain.dao.QiitaArticleDetail
import domain.dao.ZennArticleDetailDao
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ArticleRepository(
    private val httpClient: HttpClient,
    private val articleDatabase: ArticleDatabase,
) {
    suspend fun removeAll(): Boolean {
        return articleDatabase.removeAll()
    }

    suspend fun getArticle(articleId: Int): ArticleDao? {
        return articleDatabase.getArticle(articleId)
    }

    suspend fun getArticle(sourceId: String): ArticleDao? {
        return articleDatabase.getArticle(sourceId)
    }

    suspend fun getArticles(tagId: Int? = null): List<ArticleDao>? {
        return articleDatabase.getArticles(tagId)
    }

    suspend fun getArticleDetailFromMarkdown(articleId: Int): MarkdownArticleDetailDao? {
        return articleDatabase.getArticleDetailFromMarkdown(articleId)
    }

    suspend fun getArticleDetailFromQiita(articleId: Int): QiitaArticleDetail? {
        return articleDatabase.getArticleDetailFromQiita(articleId)
    }

    suspend fun getArticleDetailFromZenn(articleId: Int): ZennArticleDetailDao? {
        return articleDatabase.getArticleDetailFromZenn(articleId)
    }

    suspend fun upsertArticle(articleDao: ArticleDao): ArticleDao? {
        return articleDatabase.upsertArticle(articleDao)
    }

    suspend fun upsertMarkdownArticleDetail(markdownArticleDetailDao: MarkdownArticleDetailDao): MarkdownArticleDetailDao? {
        return articleDatabase.upsertMarkdownArticleDetail(markdownArticleDetailDao)
    }

    suspend fun upsertQiitaArticleDetail(qiitaArticleDetail: QiitaArticleDetail): QiitaArticleDetail? {
        return articleDatabase.upsertQiitaArticleDetail(qiitaArticleDetail)
    }

    suspend fun upsertZennArticleDetail(zennArticleDetailDao: ZennArticleDetailDao): ZennArticleDetailDao? {
        return articleDatabase.upsertZennArticleDetail(zennArticleDetailDao)
    }

    suspend fun getQiitaArticlesEntity(): List<QiitaArticleEntity> {
        return httpClient.get("https://qiita.com/api/v2/users/matsumo0922/items?per_page=100").body()
    }

    suspend fun getZennArticlesEntity(): ZennArticleEntity {
        return httpClient.get("https://zenn.dev/api/articles?username=matsumo0922&count=100").body()
    }

    suspend fun getZennArticleDetailEntity(sourceId: String): ZennArticleDetailEntity {
        return httpClient.get("https://zenn.dev/api/articles/$sourceId").body()
    }
}
