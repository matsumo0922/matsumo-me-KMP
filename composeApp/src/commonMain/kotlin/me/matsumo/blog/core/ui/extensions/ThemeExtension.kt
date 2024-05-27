package me.matsumo.blog.core.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import me.matsumo.blog.core.ui.theme.CONTAINER_MAX_WIDTH

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberArticleEdgeSpace(): MutableState<Dp> {
    val windowWidthSize = LocalWindowInfo.current.containerSize.width.toDp()

    return remember(windowWidthSize) {
        mutableStateOf(
            if (windowWidthSize <= CONTAINER_MAX_WIDTH) {
                8.dp
            } else {
                ((windowWidthSize - CONTAINER_MAX_WIDTH) / 2).coerceAtLeast(8.dp)
            }
        )
    }
}
