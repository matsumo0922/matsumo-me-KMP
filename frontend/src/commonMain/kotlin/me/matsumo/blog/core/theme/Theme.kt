package me.matsumo.blog.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.domain.ThemeConfig
import me.matsumo.blog.core.domain.isDark
import me.matsumo.blog.core.theme.color.DarkBlueColorScheme
import me.matsumo.blog.core.theme.color.LightBlueColorScheme

@Composable
internal fun BlogTheme(
    themeConfig: ThemeConfig = ThemeConfig.Dark,
    fonts: FontFamily? = null,
    device: Device = Device.DESKTOP,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalDevice provides device,
        LocalThemeConfig provides themeConfig,
    ) {
        MaterialTheme(
            colorScheme = if (themeConfig.isDark()) DarkBlueColorScheme else LightBlueColorScheme,
            typography = createCustomFontTypography(fonts),
            shapes = BlogShapes,
            content = content,
        )
    }
}

val LocalDevice = staticCompositionLocalOf { Device.DESKTOP }
val LocalThemeConfig = staticCompositionLocalOf { ThemeConfig.System }
