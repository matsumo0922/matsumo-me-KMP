package me.matsumo.blog.core.utils

import kotlinx.browser.document
import kotlinx.browser.window

actual val currentUrl: String
    get() = document.URL

actual fun openUrl(url: String) {
    window.open(url, "_blank")
}
