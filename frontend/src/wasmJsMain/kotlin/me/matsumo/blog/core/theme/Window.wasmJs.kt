package me.matsumo.blog.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.bindToNavigation
import androidx.navigation.encodeURIComponent
import io.github.aakira.napier.Napier
import io.ktor.http.Url
import kotlinx.browser.window
import me.matsumo.blog.core.domain.Destinations
import me.matsumo.blog.core.domain.Device
import org.w3c.dom.PopStateEvent

@Composable
actual fun rememberDeviceState(): State<Device> {
    val windowWidth by rememberWindowWidthDp()
    val device = Device.fromWidth(windowWidth)

    return remember(device) { derivedStateOf { device } }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun rememberWindowWidthDp(): State<Dp> {
    val density = LocalDensity.current
    var windowWidthPx = LocalWindowInfo.current.containerSize.width

    window.addEventListener("resize") {
        windowWidthPx = window.innerWidth
    }

    return remember(windowWidthPx) { derivedStateOf { with(density) { windowWidthPx.toDp() } } }
}

actual fun isSystemInDarkThemeUnSafe(): Boolean {
    return window.matchMedia("(prefers-color-scheme: dark)").matches
}

@OptIn(ExperimentalBrowserHistoryApi::class)
@Composable
actual fun BindToNavigation(navController: NavController) {
    LaunchedEffect(true) {
        navigateFromUrl(navController)

        window.addEventListener("popstate") { event ->
            if (event is PopStateEvent && event.state == null) {
                navigateFromUrl(navController)
            }
        }

        window.bindToNavigation(navController) {
            it.getRouteAsUrlFragment().orEmpty()
        }
    }
}

private fun navigateFromUrl(navController: NavController) {
    val url = Url(window.location.hash.replace("#", ""))
    val destinations = Destinations.fromUrl(url)

    if (destinations != null) {
        navController.navigate(destinations)
    } else {
        Napier.e("Failed to navigate from url: $url")
    }
}

private fun NavBackStackEntry.getRouteAsUrlFragment() = Destinations.fromBackStackEntry(this)?.toUrlPath()

actual fun openUrl(url: String) {
    window.open(url, "_blank")
}

actual fun mailTo(name: String, address: String, message: String) {
    val subject = encodeURIComponent("Contact from $name. (matsumo.me)")
    val body = encodeURIComponent(message)

    openUrl("mailto:$address?subject=$subject&body=$body")
}
