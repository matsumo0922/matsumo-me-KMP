package me.matsumo.blog.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonNull.content
import me.matsumo.blog.core.model.ThemeColorConfig
import me.matsumo.blog.core.theme.color.*

@Composable
internal fun MMTheme(
    shouldUseDarkTheme: Boolean = isSystemInDarkTheme(),
    fonts: FontFamily = getNotoSansJPFontFamily(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (shouldUseDarkTheme) DarkBlueColorScheme else LightBlueColorScheme,
        typography = createCustomFontTypography(fonts),
        shapes = MMShapes,
        content = content,
    )
}

val CONTAINER_MAX_WIDTH = 1024.dp
val HEADER_HEIGHT = 104.dp
