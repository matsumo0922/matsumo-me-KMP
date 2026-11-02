package domain

import kotlin.time.Instant

data class MarkdownArticleDetailEntity(
    val title: String,
    val content: String,
    val path: String,
    val tags: List<String>,
    val sha: String,
    val publishedAt: Instant,
)
