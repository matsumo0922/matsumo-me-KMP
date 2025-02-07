package me.matsumo.blog.core.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("resource")
    val resource: String,
    @SerialName("tags")
    val tags: List<String>,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("published_at")
    val publishedAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("url")
    val url: String,
)
