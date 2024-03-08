package me.matsumo.blog.core.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    @SerialName("id")
    val id: Int,
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
    val url: String
)
