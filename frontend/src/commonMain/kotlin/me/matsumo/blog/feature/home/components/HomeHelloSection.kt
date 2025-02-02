package me.matsumo.blog.feature.home.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.home_greeting1
import matsumo_me_kmp.frontend.generated.resources.home_greeting2
import matsumo_me_kmp.frontend.generated.resources.home_greeting3
import matsumo_me_kmp.frontend.generated.resources.home_greeting_description
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.theme.LocalDevice
import me.matsumo.blog.core.theme.black
import me.matsumo.blog.core.theme.center
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomeHelloSection(
    modifier: Modifier = Modifier,
) {
    val shimmerEffectTransition = rememberInfiniteTransition(label = "shimmerEffect")
    val shimmerProgressOffset by shimmerEffectTransition.animateFloat(
        initialValue = -0.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse,
        )
    )

    val defaultTextColor = MaterialTheme.colorScheme.onBackground
    val primaryTextColor = MaterialTheme.colorScheme.primary
    val tertiaryTextColor = MaterialTheme.colorScheme.tertiary

    val shimmerBrush = remember(shimmerProgressOffset) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val widthOffset = size.width * shimmerProgressOffset
                val heightOffset = size.height * shimmerProgressOffset

                return LinearGradientShader(
                    colors = listOf(primaryTextColor, tertiaryTextColor),
                    from = Offset(widthOffset, heightOffset),
                    to = Offset(widthOffset + size.width, heightOffset + size.height),
                    tileMode = TileMode.Mirror
                )
            }
        }
    }

    val greetingString = buildAnnotatedString {
        withStyle(SpanStyle(color = defaultTextColor)) {
            append(stringResource(Res.string.home_greeting1))
        }
        withStyle(SpanStyle(color = defaultTextColor.copy(alpha = 0.4f))) {
            append(", ")
        }
        withStyle(SpanStyle(color = defaultTextColor)) {
            append(stringResource(Res.string.home_greeting2) + " ")
        }
        withStyle(SpanStyle(brush = shimmerBrush)) {
            append(stringResource(Res.string.home_greeting3))
        }
    }

    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
    ) {
        Text(
            text = greetingString,
            style = MaterialTheme.typography.displayLarge.black().center(),
            fontSize = if (LocalDevice.current == Device.MOBILE) 48.sp else 72.sp,
        )

        Text(
            text = stringResource(Res.string.home_greeting_description),
            style = MaterialTheme.typography.titleLarge.center(),
            color = defaultTextColor,
        )
    }
}
