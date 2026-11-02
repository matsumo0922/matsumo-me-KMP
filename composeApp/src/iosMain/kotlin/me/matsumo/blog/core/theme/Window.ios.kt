package me.matsumo.blog.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import io.github.aakira.napier.Napier
import me.matsumo.blog.core.domain.Device
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun rememberDeviceState(): State<Device> {
    val windowWidth = rememberWindowWidthDp()
    val device = Device.fromWidth(windowWidth.value)
    return remember(device) { derivedStateOf { device } }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun rememberWindowWidthDp(): State<Dp> {
    val density = LocalDensity.current
    val width = LocalWindowInfo.current.containerSize.width

    return remember(width) { derivedStateOf { with(density) { width.toDp() } } }
}

actual fun isSystemInDarkThemeUnSafe(): Boolean {
    return true
}

@Composable
actual fun BindToNavigation(navController: NavController) {
    // do nothing
}

actual fun openUrl(url: String) {
    UIApplication.sharedApplication.openURL(NSURL(string = url), options = mapOf<Any?, Any?>()) {
        if (!it) Napier.w { "Failed to open URL: $url" }
    }
}

actual fun mailTo(name: String, address: String, message: String) {
    val subject = "Contact from $name. (matsumo.me)"
    openUrl("mailto:$address?subject=$subject&body=$message")
}

@Composable
actual fun SetWindowTitle(title: String) {
    // do nothing
}
