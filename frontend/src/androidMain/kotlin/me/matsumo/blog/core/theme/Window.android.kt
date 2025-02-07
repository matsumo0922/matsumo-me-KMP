package me.matsumo.blog.core.theme

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import io.github.aakira.napier.Napier
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

actual fun openUrl(url: String) {
    runCatching {
        val intent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .build()

        intent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.launchUrl(blogApplicationContext, url.toUri())
    }.recoverCatching {
        blogApplicationContext.startActivity(
            Intent(Intent.ACTION_VIEW, url.toUri()).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            },
        )
    }.onFailure {
        Napier.e(it) { "Failed to open the web page" }
        Toast.makeText(blogApplicationContext, "Failed to open the web page", Toast.LENGTH_SHORT).show()
    }
}

actual fun mailTo(name: String, address: String, message: String) {
    runCatching {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")

            putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
            putExtra(Intent.EXTRA_SUBJECT, "Contact from $name. (matsumo.me)")
            putExtra(Intent.EXTRA_TEXT, message)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        blogApplicationContext.startActivity(intent)
    }.onFailure {
        Napier.e(it) { "Failed to open mailer." }
        Toast.makeText(blogApplicationContext, "Failed to open mailer.", Toast.LENGTH_SHORT).show()
    }
}
