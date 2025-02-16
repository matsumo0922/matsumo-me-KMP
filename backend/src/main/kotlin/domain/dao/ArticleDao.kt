package domain.dao

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.matsumo.blog.shared.utils.InstantSerializer

@Serializable
enum class ArticleSource {
    @SerialName("markdown")
    MARKDOWN,

    @SerialName("qiita")
    QIITA,

    @SerialName("zenn")
    ZENN,
}

@Serializable
data class ArticleDao(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("source_id")
    val sourceId: String,

    @SerialName("source")
    val source: ArticleSource,

    @SerialName("title")
    val title: String,

    @SerialName("summary")
    val summary: String?,

    @SerialName("tags")
    val tags: List<String>,

    @SerialName("created_at")
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,

    @Serializable(with = InstantSerializer::class)
    @SerialName("updated_at")
    val updatedAt: Instant,
)
