package me.matsumo.blog.core.utils

actual fun log(message: String) {
    println(message)
}

actual fun log(tag: String, message: String) {
    println("$tag: $message")
}
