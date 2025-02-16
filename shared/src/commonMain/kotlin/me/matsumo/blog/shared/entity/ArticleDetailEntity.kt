package me.matsumo.blog.shared.entity

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.matsumo.blog.shared.utils.InstantSerializer

@Serializable
data class ArticleDetailEntity(
    @SerialName("id")
    val id: Int,

    @SerialName("source")
    val source: ArticleSource,

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

    @SerialName("tags")
    val tags: List<String>,

    @SerialName("is_pure_markdown")
    val isPureMarkdown: Boolean,

    @SerialName("created_at")
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,

    @SerialName("updated_at")
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant,
)
