package me.matsumo.blog.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.matsumo.blog.core.ui.component.TypingText
import me.matsumo.blog.core.ui.component.buildTypeText
import me.matsumo.blog.screen.home.item.HomeSplashScreen

@Composable
fun HomeScreen(
    component: HomeComponent,
    modifier: Modifier = Modifier,
) {
    var isFinishSplash by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "This is the home screen",
                style = MaterialTheme.typography.titleMedium,
            )

            Button(
                onClick = component::onNavigateToAbout,
            ) {
                Text(text = "Navigate to About")
            }
        }

        AnimatedVisibility(
            visible = !isFinishSplash,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            HomeSplashScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF222222)),
                onCompleted = { isFinishSplash = true }
            )
        }
    }
}
