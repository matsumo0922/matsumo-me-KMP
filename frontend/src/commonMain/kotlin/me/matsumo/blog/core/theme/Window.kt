package me.matsumo.blog.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import me.matsumo.blog.core.domain.Device

@Composable
expect fun rememberDeviceState(): State<Device>

expect fun isSystemInDarkThemeUnSafe(): Boolean
