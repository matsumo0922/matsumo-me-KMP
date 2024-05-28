package me.matsumo.blog.core.model

import io.ktor.http.Url

sealed interface WebMetadata {
    data class OG(
        val title: String,
        val description: String,
        val siteName: String,
        val image: String,
        val url: String,
        val type: String,
    ) : WebMetadata

    data class IFrame(
        val src: String,
    ) : WebMetadata

    data class Unknown(
        val url: String,
    ) : WebMetadata
}

fun getOG(src: String): WebMetadata.OG {
    fun extractMetaContent(property: String): String {
        val regex = """<meta property="$property" content="([^"]*)"""".toRegex()
        return regex.find(src)?.groups?.get(1)?.value.orEmpty()
    }

    val title = extractMetaContent("og:title")
    val description = extractMetaContent("og:description")
    val siteName = extractMetaContent("og:site_name")
    val image = extractMetaContent("og:image")
    val url = extractMetaContent("og:url")
    val type = extractMetaContent("og:type")

    return WebMetadata.OG(
        title = title,
        description = description,
        siteName = siteName,
        image = image,
        url = url,
        type = type,
    )
}

fun getIframe(url: String): WebMetadata.IFrame? {
    val twitterPattern = """<blockquote class="twitter-tweet"> <a href="{URL}">February 22, 2022</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>"""
    val youtubePattern = """<iframe width="560" height="315" src="{URL}" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>"""

    val pattern = when (Url(url).host) {
        "twitter.com", "x.com", "t.co" -> twitterPattern
        "www.youtube.com", "youtube.com", "youtu.be" -> youtubePattern
        else -> return null
    }

    return WebMetadata.IFrame(pattern.replace("""\{URL\}""".toRegex(), url))
}