package domain

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.matsumo.blog.shared.utils.InstantSerializer

@Serializable
data class ZennArticleEntity(
    @SerialName("articles")
    val articles: List<Article>,
    @SerialName("next_page")
    val nextPage: Int?,
) {
    @Serializable
    data class Article(
        @SerialName("article_type")
        val articleType: String,
        @SerialName("body_letters_count")
        val bodyLettersCount: Int,
        @SerialName("body_updated_at")
        @Serializable(with = InstantSerializer::class)
        val bodyUpdatedAt: Instant,
        @SerialName("bookmarked_count")
        val bookmarkedCount: Int,
        @SerialName("comments_count")
        val commentsCount: Int,
        @SerialName("emoji")
        val emoji: String,
        @SerialName("id")
        val id: Int,
        @SerialName("is_suspending_private")
        val isSuspendingPrivate: Boolean,
        @SerialName("liked_count")
        val likedCount: Int,
        @SerialName("path")
        val path: String,
        @SerialName("pinned")
        val pinned: Boolean,
        @SerialName("post_type")
        val postType: String,
        @SerialName("published_at")
        @Serializable(with = InstantSerializer::class)
        val publishedAt: Instant,
        @SerialName("slug")
        val slug: String,
        @SerialName("title")
        val title: String,
    )
}
