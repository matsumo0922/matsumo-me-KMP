package me.matsumo.blog.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import me.matsumo.blog.core.model.ThemeColorConfig
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.ui.theme.color.DarkBlueColorScheme
import me.matsumo.blog.core.ui.theme.color.DarkBrownColorScheme
import me.matsumo.blog.core.ui.theme.color.DarkGreenColorScheme
import me.matsumo.blog.core.ui.theme.color.DarkPinkColorScheme
import me.matsumo.blog.core.ui.theme.color.DarkPurpleColorScheme
import me.matsumo.blog.core.ui.theme.color.LightBlueColorScheme
import me.matsumo.blog.core.ui.theme.color.LightBrownColorScheme
import me.matsumo.blog.core.ui.theme.color.LightGreenColorScheme
import me.matsumo.blog.core.ui.theme.color.LightPinkColorScheme
import me.matsumo.blog.core.ui.theme.color.LightPurpleColorScheme

@Composable
internal fun MMTheme(
    themeConfig: ThemeConfig = ThemeConfig.System,
    themeColorConfig: ThemeColorConfig = ThemeColorConfig.Blue,
    windowWidthSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    content: @Composable () -> Unit,
) {
    val shouldUseDarkTheme = shouldUseDarkTheme(themeConfig)

    val colorScheme = when (themeColorConfig) {
        ThemeColorConfig.Blue -> if (shouldUseDarkTheme) DarkBlueColorScheme else LightBlueColorScheme
        ThemeColorConfig.Brown -> if (shouldUseDarkTheme) DarkBrownColorScheme else LightBrownColorScheme
        ThemeColorConfig.Green -> if (shouldUseDarkTheme) DarkGreenColorScheme else LightGreenColorScheme
        ThemeColorConfig.Purple -> if (shouldUseDarkTheme) DarkPurpleColorScheme else LightPurpleColorScheme
        ThemeColorConfig.Pink -> if (shouldUseDarkTheme) DarkPinkColorScheme else LightPinkColorScheme
    }

    CompositionLocalProvider(
        LocalWindowWidthSize provides windowWidthSize,
        LocalThemeConfig provides themeConfig,
        LocalSnackbarHostState provides remember { SnackbarHostState() },
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = createCustomFontTypography(getNotoSansJPFontFamily()),
            shapes = MMShapes,
            content = content,
        )
    }
}

@Composable
fun shouldUseDarkTheme(themeConfig: ThemeConfig): Boolean {
    return when (themeConfig) {
        ThemeConfig.System -> isSystemInDarkTheme()
        ThemeConfig.Light -> false
        ThemeConfig.Dark -> true
    }
}

fun calculateWindowWidthSize(width: Int): WindowWidthSizeClass {
    return when {
        width < 680 -> WindowWidthSizeClass.Compact
        width < 960 -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }
}

fun WindowWidthSizeClass.isBiggerThan(other: WindowWidthSizeClass): Boolean {
    return this.getInternalNumber() > other.getInternalNumber()
}

fun WindowWidthSizeClass.isSmallerThan(other: WindowWidthSizeClass): Boolean {
    return this.getInternalNumber() < other.getInternalNumber()
}

private fun WindowWidthSizeClass.getInternalNumber(): Int {
    return when (this) {
        WindowWidthSizeClass.Compact -> 1
        WindowWidthSizeClass.Medium -> 2
        WindowWidthSizeClass.Expanded -> 3
        else -> 0
    }
}

val LocalWindowWidthSize = staticCompositionLocalOf { WindowWidthSizeClass.Compact }
val LocalThemeConfig = staticCompositionLocalOf { ThemeConfig.System }
val LocalSnackbarHostState = staticCompositionLocalOf { SnackbarHostState() }

val CONTAINER_MAX_WIDTH = 1200.dp
val HEADER_HEIGHT = 104.dp
