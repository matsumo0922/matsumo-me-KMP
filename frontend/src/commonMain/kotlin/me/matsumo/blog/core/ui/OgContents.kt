package me.matsumo.blog.core.ui

import androidx.compose.runtime.Stable

@Stable
data class OgContents(
    val title: String,
    val description: String,
    val imageUrl: String,
    val iconUrl: String,
    val siteName: String,
)
