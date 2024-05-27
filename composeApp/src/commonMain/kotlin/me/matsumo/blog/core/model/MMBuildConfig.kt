package me.matsumo.blog.core.model

data class MMBuildConfig(
    val versionCode: String,
    val versionName: String,
) {
    companion object {
        fun dummy(): MMBuildConfig {
            return MMBuildConfig(
                versionCode = "223",
                versionName = "1.4.21",
            )
        }
    }
}
