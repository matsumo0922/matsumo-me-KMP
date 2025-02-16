package domain.dao

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.matsumo.blog.shared.utils.InstantSerializer

@Serializable
data class MarkdownArticleDetailDao(
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
    val isPublished: Boolean,

    @SerialName("tags")
    val tags: List<String>,

    @SerialName("created_at")
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,

    @SerialName("updated_at")
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant,
)
