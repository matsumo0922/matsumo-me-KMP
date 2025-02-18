package me.matsumo.blog.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class OgContents(
    val title: String,
    val description: String,
    val imageUrl: String,
    val iconUrl: String,
    val siteName: String,
)
