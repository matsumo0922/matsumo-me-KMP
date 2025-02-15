package domain.dao

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import utils.OffsetDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class ZennArticleDetailDao(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("article_id")
    val articleId: Int,

    @SerialName("source_id")
    val sourceId: String,

    @SerialName("source_url")
    val sourceUrl: String,

    @SerialName("title")
    val title: String,

    @SerialName("content")
    val content: String?,

    @SerialName("rendered_content")
    val renderedContent: String?,

    @SerialName("is_published")
    val isPublished: Boolean?,

    @SerialName("emoji")
    val emoji: String?,

    @SerialName("article_type")
    val articleType: String?,

    @SerialName("tags")
    val tags: List<String>,

    @Serializable(with = OffsetDateTimeSerializer::class)
    @SerialName("created_at")
    val createdAt: OffsetDateTime,

    @Serializable(with = OffsetDateTimeSerializer::class)
    @SerialName("updated_at")
    val updatedAt: OffsetDateTime,
)
