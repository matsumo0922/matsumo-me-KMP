package domain

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.matsumo.blog.shared.utils.InstantSerializer

@Serializable
data class QiitaArticleEntity(
    @SerialName("body")
    val body: String,
    @SerialName("coediting")
    val coediting: Boolean,
    @SerialName("comments_count")
    val commentsCount: Int,
    @SerialName("created_at")
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
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
    @Serializable(with = InstantSerializer::class)
    @SerialName("updated_at")
    val updatedAt: Instant,
    @SerialName("url")
    val url: String,
) {
    @Serializable
    data class Tag(
        @SerialName("name")
        val name: String,
    )
}
