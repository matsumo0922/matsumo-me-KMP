package domain.dao

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import utils.OffsetDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class MarkdownArticleDetailDao(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("article_id")
    val articleId: Int,

    @SerialName("source_url")
    val sourceUrl: String,

    @SerialName("title")
    val title: String,

    @SerialName("content")
    val content: String?,

    @SerialName("rendered_content")
    val renderedContent: String?,

    @SerialName("is_published")
    val isPublished: Boolean,

    @SerialName("last_modified")
    @Serializable(with = OffsetDateTimeSerializer::class)
    val lastModified: OffsetDateTime?,

    @SerialName("tags")
    val tags: List<String>,

    @SerialName("created_at")
    @Serializable(with = OffsetDateTimeSerializer::class)
    val createdAt: OffsetDateTime,

    @SerialName("updated_at")
    @Serializable(with = OffsetDateTimeSerializer::class)
    val updatedAt: OffsetDateTime,
)
