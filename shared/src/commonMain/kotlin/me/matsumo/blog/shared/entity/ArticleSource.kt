package me.matsumo.blog.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ArticleSource {
    @SerialName("markdown")
    MARKDOWN,

    @SerialName("qiita")
    QIITA,

    @SerialName("zenn")
    ZENN,
}
