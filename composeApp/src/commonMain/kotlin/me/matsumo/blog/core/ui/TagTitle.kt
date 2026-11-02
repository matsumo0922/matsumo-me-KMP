package me.matsumo.blog.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import me.matsumo.blog.core.theme.extraBold

@Composable
fun TagTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    val shimmerBrush = rememberGradientShimmerBrush()
    val title = buildAnnotatedString {
        withStyle(SpanStyle(brush = shimmerBrush)) {
            append("# $text")
        }
    }

    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.headlineMedium.extraBold(),
    )
}
