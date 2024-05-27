package me.matsumo.blog.core.common.extensions

import kotlinx.browser.document
import kotlinx.browser.window

expect val currentUrl: String
expect fun openUrl(url: String)
