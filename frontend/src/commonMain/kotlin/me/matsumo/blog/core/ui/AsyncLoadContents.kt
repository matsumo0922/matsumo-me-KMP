package me.matsumo.blog.core.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.matsumo.blog.core.domain.ScreenState

@Composable
fun <T> AsyncLoadContents(
    screenState: ScreenState<T>,
    modifier: Modifier = Modifier,
    otherModifier: Modifier = modifier,
    containerColor: Color = Color.Transparent,
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
