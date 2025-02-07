package me.matsumo.blog.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavController
import me.matsumo.blog.core.domain.Device

@Composable
expect fun rememberDeviceState(): State<Device>

@Composable
expect fun BindToNavigation(navController: NavController)

expect fun openUrl(url: String)

expect fun mailTo(name: String, address: String, message: String)

expect fun isSystemInDarkThemeUnSafe(): Boolean
