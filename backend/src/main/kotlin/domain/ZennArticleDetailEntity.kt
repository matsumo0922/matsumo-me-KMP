package domain


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import utils.OffsetDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class ZennArticleDetailEntity(
    @SerialName("article")
    val article: Article
) {
    @Serializable
    data class Article(
        @SerialName("article_type")
        val articleType: String,
        @SerialName("body_html")
        val bodyHtml: String,
        @SerialName("body_letters_count")
        val bodyLettersCount: Int,
        @SerialName("body_updated_at")
        @Serializable(with = OffsetDateTimeSerializer::class)
        val bodyUpdatedAt: OffsetDateTime,
        @SerialName("bookmarked_count")
        val bookmarkedCount: Int,
        @SerialName("can_send_badge")
        val canSendBadge: Boolean,
        @SerialName("comments_count")
        val commentsCount: Int,
        @SerialName("current_user_bookmarked")
        val currentUserBookmarked: Boolean,
        @SerialName("current_user_liked")
        val currentUserLiked: Boolean,
        @SerialName("draft_reveal_scope")
        val draftRevealScope: String,
        @SerialName("emoji")
        val emoji: String,
        @SerialName("id")
        val id: Int,
        @SerialName("is_mine")
        val isMine: Boolean,
        @SerialName("is_preview")
        val isPreview: Boolean,
        @SerialName("is_suspending_private")
        val isSuspendingPrivate: Boolean,
        @SerialName("liked_count")
        val likedCount: Int,
        @SerialName("og_image_url")
        val ogImageUrl: String,
        @SerialName("path")
        val path: String,
        @SerialName("pinned")
        val pinned: Boolean,
        @SerialName("positive_comments_count")
        val positiveCommentsCount: Int,
        @SerialName("post_type")
        val postType: String,
        @SerialName("published_at")
        @Serializable(with = OffsetDateTimeSerializer::class)
        val publishedAt: OffsetDateTime,
        @SerialName("should_noindex")
        val shouldNoindex: Boolean,
        @SerialName("slug")
        val slug: String,
        @SerialName("status")
        val status: String,
        @SerialName("title")
        val title: String,
        @SerialName("topics")
        val topics: List<Topic>,
    ) {
        @Serializable
        data class Topic(
            @SerialName("display_name")
            val displayName: String,
            @SerialName("id")
            val id: Int,
            @SerialName("image_url")
            val imageUrl: String,
            @SerialName("name")
            val name: String,
            @SerialName("taggings_count")
            val taggingsCount: Int
        )
    }
}