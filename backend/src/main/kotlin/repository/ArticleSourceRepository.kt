package repository

import domain.MarkdownArticleDetailEntity
import domain.MarkdownArticleEntity
import domain.QiitaArticleEntity
import domain.ZennArticleDetailEntity
import domain.ZennArticleEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.commonmark.ext.front.matter.YamlFrontMatterBlock
import org.commonmark.ext.front.matter.YamlFrontMatterExtension
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor
import org.commonmark.parser.Parser
import org.commonmark.renderer.markdown.MarkdownRenderer

/** Qiita / Zenn / GitHub から記事の元データを取得する */
class ArticleSourceRepository(
    private val httpClient: HttpClient,
) {
    suspend fun getQiitaArticlesEntity(): List<QiitaArticleEntity> = httpClient.get("https://qiita.com/api/v2/users/matsumo0922/items?per_page=100").body()

    suspend fun getZennArticlesEntity(): ZennArticleEntity = httpClient.get("https://zenn.dev/api/articles?username=matsumo0922&count=100").body()

    suspend fun getZennArticleDetailEntity(sourceId: String): ZennArticleDetailEntity = httpClient.get("https://zenn.dev/api/articles/$sourceId").body()

    suspend fun getMarkdownArticlesEntity(): List<MarkdownArticleEntity> = httpClient.get("https://api.github.com/repos/matsumo0922/matsumo-me-KMP/contents/articles").body()

    suspend fun getMarkdownArticleDetailEntity(path: String, sha: String): MarkdownArticleDetailEntity {
        val content = httpClient.get("https://raw.githubusercontent.com/matsumo0922/matsumo-me-KMP/refs/heads/main/$path").bodyAsText()

        val renderer = MarkdownRenderer.builder().build()
        val visitor = YamlFrontMatterVisitor()
        val parser = Parser.Builder()
            .extensions(setOf(YamlFrontMatterExtension.create()))
            .build()

        val document = parser.parse(content)

        document.accept(visitor)

        var node = document.firstChild

        while (node != null) {
            val next = node.next
            if (node is YamlFrontMatterBlock) {
                node.unlink()
            }
            node = next
        }

        return MarkdownArticleDetailEntity(
            title = (visitor.data["title"] as List<String>).first(),
            tags = (visitor.data["tags"] as List<String>).first().trim('[', ']').split(",").map { it.trim(' ', '"') },
            content = renderer.render(document),
            sha = sha,
            path = path,
            publishedAt = LocalDateTime.parse((visitor.data["published_at"] as List<String>).first()).toInstant(TimeZone.of("Asia/Tokyo")),
        )
    }
}
