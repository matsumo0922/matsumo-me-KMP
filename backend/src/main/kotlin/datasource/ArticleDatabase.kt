package datasource

import domain.dao.ArticleDao
import domain.dao.MarkdownArticleDetailDao
import domain.dao.QiitaArticleDetail
import domain.dao.ZennArticleDetailDao
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class ArticleDatabase(
    private val supabaseClient: SupabaseClient,
) {
    suspend fun removeAll(): Boolean = runCatching {
        supabaseClient.from("article").delete()
        supabaseClient.from("markdown_article_detail").delete()
        supabaseClient.from("qiita_article_detail").delete()
        supabaseClient.from("zenn_article_detail").delete()
    }.isSuccess

    suspend fun getArticle(articleId: Int): ArticleDao? {
        return supabaseClient.from("article")
            .select {
                filter { ArticleDao::id eq articleId }
            }
            .decodeSingleOrNull<ArticleDao>()
    }

    suspend fun getArticle(sourceId: String): ArticleDao? {
        return supabaseClient.from("article")
            .select {
                filter { ArticleDao::sourceId eq sourceId }
            }
            .decodeSingleOrNull<ArticleDao>()
    }

    suspend fun getArticles(tagId: Int? = null): List<ArticleDao>? {
        return supabaseClient.from("article")
            .select {
                if (tagId != null) {
                    filter { ArticleDao::tags contains listOf(tagId) }
                }
            }
            .decodeAsOrNull<List<ArticleDao>>()
    }

    suspend fun getArticleDetailFromMarkdown(articleId: Int): MarkdownArticleDetailDao? {
        return supabaseClient.from("markdown_article_detail")
            .select {
                filter { MarkdownArticleDetailDao::articleId eq articleId }
            }
            .decodeSingleOrNull<MarkdownArticleDetailDao>()
    }

    suspend fun getArticleDetailFromQiita(articleId: Int): QiitaArticleDetail? {
        return supabaseClient.from("qiita_article_detail")
            .select {
                filter { QiitaArticleDetail::articleId eq articleId }
            }
            .decodeSingleOrNull<QiitaArticleDetail>()
    }

    suspend fun getArticleDetailFromZenn(articleId: Int): ZennArticleDetailDao? {
        return supabaseClient.from("zenn_article_detail")
            .select {
                filter { ZennArticleDetailDao::articleId eq articleId }
            }
            .decodeSingleOrNull<ZennArticleDetailDao>()
    }

    suspend fun upsertArticle(article: ArticleDao): ArticleDao? {
        return supabaseClient.from("article")
            .upsert(article) {
                select()
            }.decodeSingleOrNull<ArticleDao>()
    }

    suspend fun upsertMarkdownArticleDetail(markdownArticleDetail: MarkdownArticleDetailDao): MarkdownArticleDetailDao? {
        return supabaseClient.from("markdown_article_detail")
            .upsert(markdownArticleDetail) {
                select()
            }
            .decodeSingleOrNull<MarkdownArticleDetailDao>()
    }

    suspend fun upsertQiitaArticleDetail(qiitaArticleDetail: QiitaArticleDetail): QiitaArticleDetail? {
        return supabaseClient.from("qiita_article_detail")
            .upsert(qiitaArticleDetail) {
                select()
            }
            .decodeSingleOrNull<QiitaArticleDetail>()
    }

    suspend fun upsertZennArticleDetail(zennArticleDetail: ZennArticleDetailDao): ZennArticleDetailDao? {
        return supabaseClient.from("zenn_article_detail")
            .upsert(zennArticleDetail) {
                select()
            }
            .decodeSingleOrNull<ZennArticleDetailDao>()
    }
}
