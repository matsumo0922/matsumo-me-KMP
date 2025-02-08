package me.matsumo.blog.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
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
import me.matsumo.blog.core.ui.utils.toUrlPath
import org.w3c.dom.PopStateEvent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun rememberDeviceState(): State<Device> {
    val density = LocalDensity.current
    val windowWidth = with(density) { LocalWindowInfo.current.containerSize.width.toDp() }
    var device = Device.fromWidth(windowWidth)

    window.addEventListener("resize", {
        device = Device.fromWidth(with(density) { window.innerWidth.toDp() })
    })

    return remember(device) { derivedStateOf { device } }
}

actual fun isSystemInDarkThemeUnSafe(): Boolean {
    return window.matchMedia("(prefers-color-scheme: dark)").matches
}

@OptIn(ExperimentalBrowserHistoryApi::class)
@Composable
actual fun BindToNavigation(navController: NavController) {
    LaunchedEffect(true) {
        navigateFromUrl(navController)

        window.addEventListener("popstate", {
            if (it is PopStateEvent && it.state == null) {
                navigateFromUrl(navController)
            }
        })

        window.bindToNavigation(navController) {
            it.getRouteAsUrlFragment()
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

private fun NavBackStackEntry.getRouteAsUrlFragment() =
    toUrlPath()?.let { r -> "#${encodeURIComponent(r)}" }.orEmpty()

actual fun openUrl(url: String) {
    window.open(url, "_blank")
}

actual fun mailTo(name: String, address: String, message: String) {
    val subject = encodeURIComponent("Contact from $name. (matsumo.me)")
    val body = encodeURIComponent(message)

    openUrl("mailto:$address?subject=$subject&body=$body")
}
