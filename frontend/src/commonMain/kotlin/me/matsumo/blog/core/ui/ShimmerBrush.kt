package me.matsumo.blog.core.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun rememberGradientShimmerBrush(
    colors: ImmutableList<Color> = persistentListOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.tertiary),
    durationMillis: Int = 3000,
): Brush {
    val shimmerEffectTransition = rememberInfiniteTransition(label = "shimmerEffect")
    val shimmerProgressOffset by shimmerEffectTransition.animateFloat(
        initialValue = -0.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis),
            repeatMode = RepeatMode.Reverse,
        ),
    )

    return remember(shimmerProgressOffset) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val widthOffset = size.width * shimmerProgressOffset
                val heightOffset = size.height * shimmerProgressOffset

                return LinearGradientShader(
                    colors = colors,
                    from = Offset(widthOffset, heightOffset),
                    to = Offset(widthOffset + size.width, heightOffset + size.height),
                    tileMode = TileMode.Mirror,
                )
            }
        }
    }
}


@Composable
fun Modifier.shimmer(
    show: Boolean = true,
    roundedClip: Dp = 4.dp,
    durationMillis: Int = 3000,
): Modifier {
    val brush = rememberGradientShimmerBrush(
        colors = persistentListOf(
            Color.Gray.copy(alpha = 0.3f),
            Color.Gray.copy(alpha = 0.1f),
            Color.Gray.copy(alpha = 0.3f),
        ),
        durationMillis = durationMillis,
    )

    return this
        .clip(RoundedCornerShape(roundedClip))
        .background(
            if (show) {
                brush
            } else {
                Brush.linearGradient(
                    colors = listOf(Color.Transparent, Color.Transparent),
                    start = Offset.Zero,
                    end = Offset.Zero
                )
            }
        )
}
