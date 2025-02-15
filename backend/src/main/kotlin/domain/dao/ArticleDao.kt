package domain.dao

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import utils.OffsetDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
enum class ArticleSource {
    @SerialName("markdown")
    MARKDOWN,

    @SerialName("qiita")
    QIITA,

    @SerialName("zenn")
    ZENN
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
    @Serializable(with = OffsetDateTimeSerializer::class)
    val createdAt: OffsetDateTime,

    @Serializable(with = OffsetDateTimeSerializer::class)
    @SerialName("updated_at")
    val updatedAt: OffsetDateTime
)
