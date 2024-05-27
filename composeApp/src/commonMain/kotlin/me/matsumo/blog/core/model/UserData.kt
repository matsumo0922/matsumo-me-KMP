package me.matsumo.blog.core.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val themeConfig: ThemeConfig,
    val themeColorConfig: ThemeColorConfig,
    val isAgreedPrivacyPolicy: Boolean,
    val isAgreedTermsOfService: Boolean,
) {
    companion object {
        fun default(): UserData {
            return UserData(
                themeConfig = ThemeConfig.System,
                themeColorConfig = ThemeColorConfig.Blue,
                isAgreedPrivacyPolicy = false,
                isAgreedTermsOfService = false,
            )
        }
    }
}
