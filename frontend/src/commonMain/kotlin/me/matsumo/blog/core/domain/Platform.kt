package me.matsumo.blog.core.domain

enum class Platform {
    ANDROID,
    IOS,
    WEB;
}

expect val platform: Platform
