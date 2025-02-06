package me.matsumo.blog.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import me.matsumo.blog.core.ui.rememberShimmerBrush
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomeHelloSection(
    modifier: Modifier = Modifier,
) {
    val shimmerBrush = rememberShimmerBrush()
    val defaultTextColor = MaterialTheme.colorScheme.onBackground

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
