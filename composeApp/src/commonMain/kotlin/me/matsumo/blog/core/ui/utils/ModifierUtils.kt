package me.matsumo.blog.core.ui.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.KeyInputModifierNode
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.PointerInputModifierNode
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

// Modifier 構成: atOnce の表示済み状態を rememberSaveable で保持するため、composition を持たない Modifier.Node にできない
@Suppress("ModifierComposed")
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

    SideEffect {
        visible = true
    }

    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else initialOffsetY,
        animationSpec = tween(durationMillis, delayMillis + 200),
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis, delayMillis + 200),
    )

    this
        .offset(y = offsetY)
        .alpha(alpha)
}

fun Modifier.focusScale(
    focusedScale: Float = 1.02f,
    animationDurationMillis: Int = 400,
): Modifier = this then FocusScaleElement(focusedScale, animationDurationMillis)

fun Modifier.moveFocusOnTab(): Modifier = this then MoveFocusOnTabElement

fun Modifier.clickableWithPointer(
    interactionSource: MutableInteractionSource?,
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
) = this
    .pointerHoverIcon(PointerIcon.Hand)
    .clickable(
        interactionSource = interactionSource,
        indication = indication,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick,
    )

fun Modifier.clickableWithPointer(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
) = this
    .pointerHoverIcon(PointerIcon.Hand)
    .clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick,
    )

private data class FocusScaleElement(
    val focusedScale: Float,
    val animationDurationMillis: Int,
) : ModifierNodeElement<FocusScaleNode>() {
    override fun create(): FocusScaleNode = FocusScaleNode(focusedScale, animationDurationMillis)

    override fun update(node: FocusScaleNode) {
        node.focusedScale = focusedScale
        node.animationDurationMillis = animationDurationMillis
    }
}

/** ポインタが乗っている間だけ要素を拡大する */
private class FocusScaleNode(
    var focusedScale: Float,
    var animationDurationMillis: Int,
) : Modifier.Node(),
    PointerInputModifierNode,
    LayoutModifierNode {

    private val scale = Animatable(1f)

    // 縮小アニメーションの途中で detach されると coroutineScope ごとキャンセルされ、拡大率が途中の値で残る。
    // LazyColumn などで再利用されたときに拡大したままにならないよう、attach / reset のたびに等倍へ戻す
    override fun onAttach() {
        resetScale()
    }

    override fun onReset() {
        resetScale()
    }

    override fun onPointerEvent(
        pointerEvent: PointerEvent,
        pass: PointerEventPass,
        bounds: IntSize,
    ) {
        if (pass != PointerEventPass.Main) return

        when (pointerEvent.type) {
            PointerEventType.Enter -> animateScaleTo(focusedScale)
            PointerEventType.Exit -> animateScaleTo(1f)
        }
    }

    override fun onCancelPointerInput() {
        animateScaleTo(1f)
    }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        val placeable = measurable.measure(constraints)

        return layout(placeable.width, placeable.height) {
            placeable.placeWithLayer(0, 0) {
                scaleX = scale.value
                scaleY = scale.value
            }
        }
    }

    private fun resetScale() {
        if (!isAttached) return

        coroutineScope.launch {
            scale.snapTo(1f)
        }
    }

    private fun animateScaleTo(targetValue: Float) {
        coroutineScope.launch {
            scale.animateTo(targetValue, tween(animationDurationMillis))
        }
    }
}

private data object MoveFocusOnTabElement : ModifierNodeElement<MoveFocusOnTabNode>() {
    override fun create(): MoveFocusOnTabNode = MoveFocusOnTabNode()

    override fun update(node: MoveFocusOnTabNode) = Unit
}

/** Tab キーでフォーカスを次の要素へ、Shift + Tab で前の要素へ移す */
private class MoveFocusOnTabNode :
    Modifier.Node(),
    KeyInputModifierNode,
    CompositionLocalConsumerModifierNode {

    override fun onPreKeyEvent(event: KeyEvent): Boolean {
        if (event.type != KeyEventType.KeyDown || event.key != Key.Tab) return false

        val direction = if (event.isShiftPressed) FocusDirection.Previous else FocusDirection.Next
        currentValueOf(LocalFocusManager).moveFocus(direction)

        return true
    }

    override fun onKeyEvent(event: KeyEvent): Boolean = false
}
