package me.matsumo.blog.core.ui.utils

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.enterAnimation(
    delayMillis: Int = 0,
    durationMillis: Int = 1000,
    initialOffsetY: Dp = 24.dp,
    atOnce: Boolean = true,
): Modifier = composed {
    var visible by if (atOnce) {
        rememberSaveable { mutableStateOf(false) }
    } else {
        remember { mutableStateOf(false) }
    }

    LaunchedEffect(Unit) {
        visible = true
    }

    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else initialOffsetY,
        animationSpec = tween(durationMillis, delayMillis + 200)
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis, delayMillis + 200)
    )

    this
        .offset(y = offsetY)
        .alpha(alpha)
}

fun Modifier.focusScale(
    focusedScale: Float = 1.02f,
    animationDurationMillis: Int = 400
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isHovered) focusedScale else 1f,
        animationSpec = tween(animationDurationMillis)
    )

    this
        .hoverable(interactionSource)
        .scale(scale)
}
