package me.matsumo.blog.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import me.matsumo.blog.core.model.ThemeColorConfig
import me.matsumo.blog.core.theme.color.*

val LightDefaultColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    primaryContainer = Purple90,
    onPrimaryContainer = Purple10,
    secondary = Orange40,
    onSecondary = Color.White,
    secondaryContainer = Orange90,
    onSecondaryContainer = Orange10,
    tertiary = Blue40,
    onTertiary = Color.White,
    tertiaryContainer = Blue90,
    onTertiaryContainer = Blue10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = DarkPurpleGray99,
    onBackground = DarkPurpleGray10,
    surface = DarkPurpleGray99,
    onSurface = DarkPurpleGray10,
    surfaceVariant = PurpleGray90,
    onSurfaceVariant = PurpleGray30,
    inverseSurface = DarkPurpleGray20,
    inverseOnSurface = DarkPurpleGray95,
    outline = PurpleGray50,
)

val DarkDefaultColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = Purple20,
    primaryContainer = Purple30,
    onPrimaryContainer = Purple90,
    secondary = Orange80,
    onSecondary = Orange20,
    secondaryContainer = Orange30,
    onSecondaryContainer = Orange90,
    tertiary = Blue80,
    onTertiary = Blue20,
    tertiaryContainer = Blue30,
    onTertiaryContainer = Blue90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = DarkPurpleGray10,
    onBackground = DarkPurpleGray90,
    surface = DarkPurpleGray10,
    onSurface = DarkPurpleGray90,
    surfaceVariant = PurpleGray30,
    onSurfaceVariant = PurpleGray80,
    inverseSurface = DarkPurpleGray90,
    inverseOnSurface = DarkPurpleGray10,
    outline = PurpleGray60,
)

@Composable
internal fun MMTheme(
    themeColor: ThemeColorConfig = ThemeColorConfig.Red,
    shouldUseDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (shouldUseDarkTheme) {
        when (themeColor) {
            ThemeColorConfig.Blue -> DarkBlueColorScheme
            ThemeColorConfig.Brown -> DarkBrownColorScheme
            ThemeColorConfig.Green -> DarkGreenColorScheme
            ThemeColorConfig.Pink -> DarkPinkColorScheme
            ThemeColorConfig.Purple -> DarkPurpleColorScheme
            else -> DarkDefaultColorScheme
        }
    } else {
        when (themeColor) {
            ThemeColorConfig.Blue -> LightBlueColorScheme
            ThemeColorConfig.Brown -> LightBrownColorScheme
            ThemeColorConfig.Green -> LightGreenColorScheme
            ThemeColorConfig.Pink -> LightPinkColorScheme
            ThemeColorConfig.Purple -> LightPurpleColorScheme
            else -> LightDefaultColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = createCustomFontTypography(),
        shapes = MMShapes,
        content = content,
    )
}
