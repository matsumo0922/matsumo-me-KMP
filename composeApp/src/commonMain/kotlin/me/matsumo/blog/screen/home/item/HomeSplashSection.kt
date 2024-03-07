package me.matsumo.blog.screen.home.item

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.matsumo.blog.core.ui.component.AnimatedText
import me.matsumo.blog.core.ui.component.buildTextAnimateItems

@Composable
internal fun HomeSplashSection(
    modifier: Modifier = Modifier,
) {
    AnimatedText(
        modifier = modifier,
        animateItems = buildTextAnimateItems("./gradlew", 50, "composeApp:", 75, "wasmJsBrowserRun"),
        style = MaterialTheme.typography.titleMedium,
    )
}
