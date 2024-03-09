package me.matsumo.blog.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.JsonNull.content
import me.matsumo.blog.core.model.ThemeColorConfig
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.model.WindowWidthSize
import me.matsumo.blog.core.theme.color.*
import me.matsumo.blog.core.utils.log

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun MMTheme(
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

val CONTAINER_MAX_WIDTH = 1072.dp
val HEADER_HEIGHT = 104.dp
