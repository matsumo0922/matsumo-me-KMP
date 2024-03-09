package me.matsumo.blog.core.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.ui.component.ErrorView
import me.matsumo.blog.core.ui.component.HeaderView
import me.matsumo.blog.core.ui.component.LoadingView

@Composable
fun <T> AsyncLoadContents(
    screenState: ScreenState<T>,
    modifier: Modifier = Modifier,
    otherModifier: Modifier = modifier,
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
                    modifier = otherModifier.fillMaxWidth(),
                )
            }
            is ScreenState.Error -> {
                ErrorView(
                    modifier = otherModifier.fillMaxWidth(),
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
    otherModifier: Modifier = modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    cornerShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    retryAction: () -> Unit = {},
    content: @Composable (T) -> Unit,
) {
    Box(modifier.background(containerColor)) {
        AsyncLoadContents(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize(),
            otherModifier = otherModifier,
            screenState = screenState,
            containerColor = containerColor,
            cornerShape = cornerShape,
            retryAction = retryAction,
            content = content,
        )

        HeaderView(
            modifier = Modifier.fillMaxWidth(),
            onSetThemeConfig = onSetThemeConfig,
        )
    }
}
