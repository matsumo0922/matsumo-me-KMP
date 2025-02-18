package me.matsumo.blog.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OgContentsEntity(
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("icon_url")
    val iconUrl: String,
    @SerialName("site_name")
    val siteName: String,
)
