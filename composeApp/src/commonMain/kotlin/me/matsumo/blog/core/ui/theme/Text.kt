package me.matsumo.blog.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import matsumo_me.composeapp.generated.resources.CaskaydiaCoveNerdFont_Bold
import matsumo_me.composeapp.generated.resources.CaskaydiaCoveNerdFont_BoldItalic
import matsumo_me.composeapp.generated.resources.CaskaydiaCoveNerdFont_Italic
import matsumo_me.composeapp.generated.resources.CaskaydiaCoveNerdFont_Light
import matsumo_me.composeapp.generated.resources.CaskaydiaCoveNerdFont_LightItalic
import matsumo_me.composeapp.generated.resources.CaskaydiaCoveNerdFont_Regular
import matsumo_me.composeapp.generated.resources.NotoSansJP_Bold
import matsumo_me.composeapp.generated.resources.NotoSansJP_ExtraBold
import matsumo_me.composeapp.generated.resources.NotoSansJP_ExtraLight
import matsumo_me.composeapp.generated.resources.NotoSansJP_Light
import matsumo_me.composeapp.generated.resources.NotoSansJP_Medium
import matsumo_me.composeapp.generated.resources.NotoSansJP_Regular
import matsumo_me.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
internal fun createCustomFontTypography(font: FontFamily): Typography {
    return Typography(
        displayLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 45.sp,
            lineHeight = 52.sp,
            letterSpacing = 0.sp,
        ),
        displaySmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.1.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp,
        ),
    )
}

@Composable
fun getNotoSansJPFontFamily() = FontFamily(
    Font(Res.font.NotoSansJP_Bold, FontWeight.Bold),
    Font(Res.font.NotoSansJP_ExtraBold, FontWeight.ExtraBold),
    Font(Res.font.NotoSansJP_Light, FontWeight.Light),
    Font(Res.font.NotoSansJP_ExtraLight, FontWeight.ExtraLight),
    Font(Res.font.NotoSansJP_Regular, FontWeight.Normal),
    Font(Res.font.NotoSansJP_Medium, FontWeight.Medium),
)

@Composable
fun getCaskaydiaCoveNerdFontFamily() = FontFamily(
    Font(Res.font.CaskaydiaCoveNerdFont_Bold, FontWeight.Bold),
    Font(Res.font.CaskaydiaCoveNerdFont_BoldItalic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.CaskaydiaCoveNerdFont_Light, FontWeight.Light),
    Font(Res.font.CaskaydiaCoveNerdFont_LightItalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.CaskaydiaCoveNerdFont_Regular, FontWeight.Normal),
    Font(Res.font.CaskaydiaCoveNerdFont_Italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.CaskaydiaCoveNerdFont_Regular, FontWeight.Medium),
    Font(Res.font.CaskaydiaCoveNerdFont_Italic, FontWeight.Medium, FontStyle.Italic),
)

// Align
fun TextStyle.start() = this.merge(TextStyle(textAlign = TextAlign.Start))
fun TextStyle.center() = this.merge(TextStyle(textAlign = TextAlign.Center))
fun TextStyle.end() = this.merge(TextStyle(textAlign = TextAlign.End))

// Style
fun TextStyle.bold() = this.merge(TextStyle(fontWeight = FontWeight.Bold))
fun TextStyle.extraBold() = this.merge(TextStyle(fontWeight = FontWeight.ExtraBold))
fun TextStyle.italic() = this.merge(TextStyle(fontStyle = FontStyle.Italic))

// Size
fun TextStyle.size(size: TextUnit) = this.merge(TextStyle(fontSize = size))
