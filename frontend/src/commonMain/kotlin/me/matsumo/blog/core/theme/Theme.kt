package me.matsumo.blog.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.model.WindowWidthSize
import me.matsumo.blog.core.theme.color.*
@Composable
internal fun BlogTheme(
    themeConfig: ThemeConfig = ThemeConfig.System,
    fonts: FontFamily = getNotoSansJPFontFamily(),
    windowWidthSize: WindowWidthSize = WindowWidthSize.Compact,
    content: @Composable () -> Unit,
) {
    val shouldUseDarkTheme = when (themeConfig) {
        ThemeConfig.System -> isSystemInDarkTheme()
        ThemeConfig.Light -> false
        ThemeConfig.Dark -> true
    }

    CompositionLocalProvider(
        LocalWindowWidthSize provides windowWidthSize,
        LocalThemeConfig provides themeConfig,
    ) {
        MaterialTheme(
            colorScheme = if (shouldUseDarkTheme) DarkBlueColorScheme else LightBlueColorScheme,
            typography = createCustomFontTypography(fonts),
            shapes = MMShapes,
            content = content,
        )
    }
}

val LocalWindowWidthSize = staticCompositionLocalOf<WindowWidthSize> { WindowWidthSize.Compact }
val LocalThemeConfig = staticCompositionLocalOf { ThemeConfig.System }

val CONTAINER_MAX_WIDTH = 1200.dp
val HEADER_HEIGHT = 104.dp
