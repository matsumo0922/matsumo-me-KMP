package me.matsumo.blog.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleDetail(
    val id: Long,
    val title: String,
    val body: String,
    val tags: List<String>,
    val createdAt: String,
    val updatedAt: String,
    val url: String,
)
