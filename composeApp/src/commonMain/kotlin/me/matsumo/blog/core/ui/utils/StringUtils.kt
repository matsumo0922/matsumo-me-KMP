package me.matsumo.blog.core.ui.utils

/**
 * 外部サイトの画像 URL を、この環境で読み込める URL に変換する。
 * Web は CORS ヘッダーを返さない配信元の画像を読めないため CORS プロキシを経由させ、他のプラットフォームではそのまま返す。
 */
expect fun String.toCorsProxyUrl(): String
