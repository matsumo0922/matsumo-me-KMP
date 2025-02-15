package domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import utils.OffsetDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class QiitaArticleEntity(
    @SerialName("body")
    val body: String,
    @SerialName("coediting")
    val coediting: Boolean,
    @SerialName("comments_count")
    val commentsCount: Int,
    @SerialName("created_at")
    @Serializable(with = OffsetDateTimeSerializer::class)
    val createdAt: OffsetDateTime,
    @SerialName("id")
    val id: String,
    @SerialName("likes_count")
    val likesCount: Int,
    @SerialName("page_views_count")
    val pageViewsCount: Int?,
    @SerialName("private")
    val isPrivate: Boolean,
    @SerialName("reactions_count")
    val reactionsCount: Int,
    @SerialName("rendered_body")
    val renderedBody: String,
    @SerialName("slide")
    val slide: Boolean,
    @SerialName("stocks_count")
    val stocksCount: Int,
    @SerialName("tags")
    val tags: List<Tag>,
    @SerialName("title")
    val title: String,
    @Serializable(with = OffsetDateTimeSerializer::class)
    @SerialName("updated_at")
    val updatedAt: OffsetDateTime,
    @SerialName("url")
    val url: String,
) {
    @Serializable
    data class Tag(
        @SerialName("name")
        val name: String,
    )
}
