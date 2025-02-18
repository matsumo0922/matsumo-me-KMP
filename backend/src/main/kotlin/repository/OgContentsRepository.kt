package repository

import com.fleeksoft.ksoup.Ksoup
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLBuilder
import io.ktor.http.encodedPath
import me.matsumo.blog.shared.entity.OgContentsEntity

class OgContentsRepository(
    private val httpClient: HttpClient,
) {
    suspend fun getOgContents(url: String): OgContentsEntity {
        val html = httpClient.get(url).bodyAsText()
        val document = Ksoup.parse(html)

        val title = document.head().select("meta[property=og:title]").attr("content")
        val description = document.head().select("meta[property=og:description]").attr("content")
        val imageUrl = document.head().select("meta[property=og:image]").attr("content")
        val siteName = document.head().selectFirst("meta[property=og:site_name]")?.attr("content")

        val iconUrl = URLBuilder(url).apply {
            encodedPath = "/favicon.ico"
            parameters.clear()
            fragment = ""
        }.build()

        return OgContentsEntity(
            title = title,
            description = description,
            imageUrl = imageUrl,
            iconUrl = iconUrl.toString(),
            siteName = siteName ?: iconUrl.host,
        )
    }
}
