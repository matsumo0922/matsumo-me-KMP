package me.matsumo.blog.core.domain

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier

enum class Device {
    DESKTOP,
    MOBILE;

    companion object {
        fun fromWidth(width: Dp): Device {
            return if (width < MOBILE_SCREEN_WIDTH_THRESHOLD.dp) MOBILE else DESKTOP
        }

        private const val MOBILE_SCREEN_WIDTH_THRESHOLD = 840
    }
}
