plugins {
    // Kotlin Multiplatform (KMP)
    alias(libs.plugins.kmp) apply false
    alias(libs.plugins.kmpCompose) apply false
    alias(libs.plugins.kmpComplete) apply false
    alias(libs.plugins.kmpSwiftKlib) apply false

    // Kotlin
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false

    // Others
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktorfit) apply false
    alias(libs.plugins.ksp) apply false
}
