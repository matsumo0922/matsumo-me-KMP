package me.matsumo.blog.core.domain

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

enum class ThemeConfig {
    System,
    Light,
    Dark,
}

@Composable
@ReadOnlyComposable
fun ThemeConfig.isDark(): Boolean = when (this) {
    ThemeConfig.System -> isSystemInDarkTheme()
    ThemeConfig.Light -> false
    ThemeConfig.Dark -> true
}
