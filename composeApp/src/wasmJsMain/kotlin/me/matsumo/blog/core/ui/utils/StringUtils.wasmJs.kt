package me.matsumo.blog.core.ui.utils

import io.ktor.http.encodeURLParameter

// wsrv.nl は Access-Control-Allow-Origin: * を付けて画像を返す。元の URL のクエリを壊さないようエンコードして渡す
actual fun String.toCorsProxyUrl(): String = "https://wsrv.nl/?url=${encodeURLParameter()}"
