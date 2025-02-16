package domain

import java.time.OffsetDateTime

data class MarkdownArticleDetailEntity(
    val title: String,
    val content: String,
    val path: String,
    val tags: List<String>,
    val sha: String,
    val publishedAt: OffsetDateTime,
)