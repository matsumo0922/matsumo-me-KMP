package me.matsumo.blog.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.NavController
import androidx.navigation.bindToNavigation
import kotlinx.browser.window
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.ui.utils.toUrlPath

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun rememberDeviceState(): State<Device> {
    val density = LocalDensity.current
    val windowWidth = with(density) { LocalWindowInfo.current.containerSize.width.toDp() }
    var device = Device.fromWidth(windowWidth)

    window.addEventListener("resize") {
        device = Device.fromWidth(with(density) { window.innerWidth.toDp() })
    }

    return derivedStateOf { device }
}

actual fun isSystemInDarkThemeUnSafe(): Boolean {
    return window.matchMedia("(prefers-color-scheme: dark)").matches
}

@OptIn(ExperimentalBrowserHistoryApi::class)
@Composable
actual fun BindToNavigation(navController: NavController) {
    LaunchedEffect(true) {
        window.bindToNavigation(navController) {
            it.toUrlPath().orEmpty()
        }
    }
}
