package me.matsumo.blog.core.utils

import kotlinx.browser.document

actual val currentUrl: String
    get() = document.URL
