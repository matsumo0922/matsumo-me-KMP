package me.matsumo.blog.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.im_get_on_apple_store
import matsumo_me_kmp.frontend.generated.resources.im_get_on_google_play
import me.matsumo.blog.core.theme.center
import me.matsumo.blog.core.theme.color.DarkBlueColorScheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun WasmLoadingScreen(
    modifier: Modifier = Modifier,
) {
    var shouldDisplayLoadingContents by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        delay(1500)
        shouldDisplayLoadingContents = true
    }

    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center,
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )

        if (shouldDisplayLoadingContents) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                AsyncImage(
                    modifier = Modifier.size(128.dp),
                    model = "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/jetpackcompose/jetpackcompose-original.svg",
                    contentDescription = "Compose Multiplatform",
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Loading components...",
                    style = MaterialTheme.typography.titleMedium.center(),
                    color = DarkBlueColorScheme.onSurface,
                )

                Text(
                    text = "This site is built with Compose Multiplatform.\nYou can download the app version from the links below.",
                    style = MaterialTheme.typography.bodyMedium.center(),
                    color = DarkBlueColorScheme.onSurfaceVariant,
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f, false),
                    painter = painterResource(Res.drawable.im_get_on_apple_store),
                    contentDescription = "App Store",
                )

                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f, false),
                    painter = painterResource(Res.drawable.im_get_on_google_play),
                    contentDescription = "Google Play",
                )
            }
        }
    }
}
