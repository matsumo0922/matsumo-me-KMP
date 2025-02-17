package me.matsumo.blog.shared.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.matsumo.blog.shared.entity.ArticleSource
import me.matsumo.blog.shared.utils.InstantSerializer

@Serializable
data class ArticleDetail(
    val id: Long,
    val source: ArticleSource,
    val articleId: Long,
    val sourceId: String,
    val sourceUrl: String,
    val title: String,
    val content: String,
    val tags: List<String>,
    val extraSource: ArticleSource?,
    val extraSourceUrl: String?,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant,
)
