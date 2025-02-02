package me.matsumo.blog.core.theme

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.matsumo.blog.BlogApplication
import me.matsumo.blog.blogApplicationContext
import me.matsumo.blog.core.domain.Device

@Composable
actual fun rememberDeviceState(): State<Device> {
    val windowWidth = LocalConfiguration.current.screenWidthDp
    return remember { derivedStateOf { Device.fromWidth(windowWidth.dp) } }
}

actual fun isSystemInDarkThemeUnSafe(): Boolean {
    return (blogApplicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}

@Composable
actual fun BindToNavigation(navController: NavController) {
    // do nothing
}
