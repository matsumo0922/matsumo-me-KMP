package me.matsumo.blog.screen.about

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
import me.matsumo.blog.screen.home.HomeComponent

@Composable
fun AboutScreen(
    component: AboutComponent,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Blue.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "This is the about screen",
                style = MaterialTheme.typography.titleMedium,
            )

            Button(
                onClick = component::onNavigateToHome,
            ) {
                Text(text = "Navigate to Home")
            }
        }
    }
}
