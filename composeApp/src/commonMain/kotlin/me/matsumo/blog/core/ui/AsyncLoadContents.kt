package me.matsumo.blog.core.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.ui.components.ErrorView
import me.matsumo.blog.core.ui.components.HeaderView
import me.matsumo.blog.core.ui.components.LoadingView

@Composable
fun <T> AsyncLoadContents(
    screenState: ScreenState<T>,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    cornerShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    retryAction: () -> Unit = {},
    content: @Composable (T) -> Unit,
) {
    AnimatedContent(
        modifier = modifier
            .clip(cornerShape)
            .background(containerColor),
        targetState = screenState,
        transitionSpec = { fadeIn().togetherWith(fadeOut()) },
        contentKey = { it::class.simpleName },
        label = "AsyncLoadContents",
    ) { state ->
        when (state) {
            is ScreenState.Idle -> {
                content.invoke(state.data)
            }
            is ScreenState.Loading -> {
                LoadingView(
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            is ScreenState.Error -> {
                ErrorView(
                    modifier = Modifier.fillMaxWidth(),
                    errorState = state,
                    retryAction = retryAction,
                )
            }
        }
    }
}

@Composable
fun <T> AsyncLoadContentsWithHeader(
    screenState: ScreenState<T>,
    onSetThemeConfig: (ThemeConfig) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    cornerShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    retryAction: () -> Unit = {},
    content: @Composable (T) -> Unit,
) {
    val hazeState = remember { HazeState() }

    Box(modifier.background(containerColor)) {
        AsyncLoadContents(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .haze(hazeState),
            screenState = screenState,
            containerColor = containerColor,
            cornerShape = cornerShape,
            retryAction = retryAction,
            content = content,
        )

        HeaderView(
            modifier = Modifier
                .fillMaxWidth()
                .hazeChild(hazeState),
            onSetThemeConfig = onSetThemeConfig,
        )
    }
}
