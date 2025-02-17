package me.matsumo.blog.shared.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.matsumo.blog.shared.entity.ArticleSource
import me.matsumo.blog.shared.utils.InstantSerializer

@Serializable
data class Article(
    val id: Long,
    val sourceId: String,
    val source: ArticleSource,
    val sourceUrl: String,
    val title: String,
    val summary: String?,
    val tags: List<String>,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant,
)
