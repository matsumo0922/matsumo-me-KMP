package me.matsumo.blog.app.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import kotlinx.datetime.Clock
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * 各点の状態を保持するクラス
 *
 * @param x 現在の x 座標
 * @param y 現在の y 座標
 * @param vx x 軸方向の速度（px/秒）
 * @param vy y 軸方向の速度（px/秒）
 * @param radius 描画時の半径（px）
 * @param alpha 現在の透明度（0.0〜1.0）
 * @param stableTimer 安定状態（フェード前）の残り時間（秒）
 * @param fadeTimer フェード中の残り時間（秒）
 * @param fading フェード中か否か
 * @param fadeDirection フェード方向（-1:フェードアウト、1:フェードイン、0:安定状態）
 */
private data class PointData(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val radius: Float,
    var alpha: Float = 1f,          // 初期は完全表示
    var stableTimer: Float = 0f,    // 表示（または非表示）状態が続く時間
    var fadeTimer: Float = 0f,      // フェード中の残り時間
    var fading: Boolean = false,    // フェード中なら true
    var fadeDirection: Int = 0      // -1: フェードアウト、1: フェードイン、0:安定状態
)

@Composable
fun BlogBackground(
    pointCount: Int,
    pointColor: Color,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val points = remember { mutableStateListOf<PointData>() }
    val random = remember { Random(Clock.System.now().epochSeconds) }

    BoxWithConstraints(
        modifier = modifier.background(containerColor),
        contentAlignment = Alignment.Center,
    ) {
        val widthPx = with(density) { maxWidth.toPx() }
        val heightPx = with(density) { maxHeight.toPx() }

        // recomposition 用
        val frameTick = remember { mutableStateOf(0L) }

        if (points.isEmpty()) {
            repeat(pointCount) {
                val radius = random.nextFloat() * 4f + 2f

                val x = random.nextFloat() * widthPx
                val y = random.nextFloat() * heightPx

                val speed = random.nextFloat() * 100f + 50f // px/秒

                val angle = random.nextFloat() * 2 * PI.toFloat()
                val vx = cos(angle) * speed
                val vy = sin(angle) * speed

                val stableTimer = random.nextFloat() * 8f + 2f

                points.add(
                    PointData(
                        x = x,
                        y = y,
                        vx = vx,
                        vy = vy,
                        radius = radius,
                        alpha = 1f,
                        stableTimer = stableTimer
                    )
                )
            }
        }

        LaunchedEffect(Unit) {
            var lastTime = withFrameNanos { it }

            while (true) {
                val frameTime = withFrameNanos { it }
                val deltaTime = (frameTime - lastTime) / 1_000_000_000f

                lastTime = frameTime

                points.forEach { point ->
                    point.x += (point.vx / 5f) * deltaTime
                    point.y += (point.vy / 5f) * deltaTime

                    if (point.x < 0f) {
                        point.x = widthPx
                    } else if (point.x > widthPx) {
                        point.x = 0f
                    }

                    if (point.y < 0f) {
                        point.y = heightPx
                    } else if (point.y > heightPx) {
                        point.y = 0f
                    }

                    if (!point.fading) {
                        point.stableTimer -= deltaTime

                        if (point.stableTimer <= 0f) {
                            point.fading = true
                            point.fadeTimer = 1f
                            point.fadeDirection = if (point.alpha >= 1f) -1 else if (point.alpha <= 0f) 1 else 0

                            if (point.fadeDirection == 0) {
                                point.fadeDirection = if (point.alpha > 0.5f) -1 else 1
                            }
                        }
                    } else {
                        point.fadeTimer -= deltaTime

                        val progress = (1f - (point.fadeTimer / 1f)).coerceIn(0f, 1f)

                        if (point.fadeDirection == -1) {
                            point.alpha = 1f - progress // fadeOut
                        } else if (point.fadeDirection == 1) {
                            point.alpha = progress // fadeIn
                        }

                        if (point.fadeTimer <= 0f) {
                            point.alpha = if (point.fadeDirection == -1) 0f else 1f
                            point.fading = false
                            point.fadeDirection = 0
                            point.stableTimer = random.nextFloat() * 3f + 2f
                        }
                    }
                }

                // recompose
                frameTick.value++
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            frameTick.value

            points.forEach { point ->
                drawCircle(
                    color = pointColor.copy(alpha = point.alpha),
                    radius = point.radius,
                    center = Offset(point.x, point.y)
                )
            }
        }

        content.invoke()
    }
}
