package me.matsumo.blog.core.domain

import androidx.compose.runtime.Immutable

@Immutable
data class BlogConfig(
    val versionCode: Int,
    val versionName: String,
    val backendUrl: String,
    val revision: String,
)
