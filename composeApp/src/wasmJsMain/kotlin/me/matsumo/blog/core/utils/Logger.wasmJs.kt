package me.matsumo.blog.core.utils

import kotlinx.browser.window

actual fun log(message: String) {
    println(message)
}

actual fun log(tag: String, message: String) {
    println("$tag: $message")
}
