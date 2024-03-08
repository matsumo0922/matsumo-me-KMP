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
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.ui.AsyncLoadContents
import me.matsumo.blog.core.ui.AsyncLoadContentsWithHeader
import me.matsumo.blog.core.ui.component.HeaderView
import org.koin.compose.getKoin
import org.koin.core.Koin
import org.koin.core.KoinApplication

@Composable
fun HomeScreen(
    component: HomeComponent,
    modifier: Modifier = Modifier,
) {
    val screenState by component.screenState.collectAsState()

    LaunchedEffect(Unit) {
        if (screenState !is ScreenState.Idle) {
            component.fetch()
        }
    }

    AsyncLoadContentsWithHeader(
        modifier = modifier.fillMaxSize(),
        screenState = screenState,
    ) {
        HomeIdleScreen(
            modifier = Modifier.fillMaxSize(),
            navigateToAbout = component::onNavigateToAbout,
        )
    }
}

@Composable
fun HomeIdleScreen(
    navigateToAbout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
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
                onClick = navigateToAbout,
            ) {
                Text(text = "Navigate to About")
            }
        }
    }
}
