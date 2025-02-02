package me.matsumo.blog.core.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Long,
    val title: String,
    val resource: String,
    val tags: List<String>,
    val createdAt: String,
    val publishedAt: String,
    val updatedAt: String,
    val url: String
)
