package me.matsumo.blog.core.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import me.matsumo.blog.core.theme.extraBold

@Composable
fun TagTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    val shimmerBrush = rememberShimmerBrush()
    val title = buildAnnotatedString {
        withStyle(SpanStyle(brush = shimmerBrush)) {
            append("# $text")
        }
    }

    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.headlineMedium.extraBold()
    )
}
