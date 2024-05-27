package me.matsumo.blog.core.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Int.toDp(): Dp = (this / LocalDensity.current.density).dp

@Composable
fun Dp.toPx(): Float = (this.value * LocalDensity.current.density)
