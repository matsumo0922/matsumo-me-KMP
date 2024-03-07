package me.matsumo.blog.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.matsumo.blog.core.ui.component.AnimatedText
import me.matsumo.blog.core.ui.component.buildTextAnimateItems

@Composable
fun HomeScreen(
    component: HomeComponent,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedText(
                animateItems = buildTextAnimateItems("./gradlew ", 200, "matsumo-me:", 400, "wasmJsBrowserRun"),
                style = MaterialTheme.typography.titleMedium,
            )

            Button(
                onClick = component::onNavigateToAbout,
            ) {
                Text(text = "Navigate to About")
            }
        }
    }
}
