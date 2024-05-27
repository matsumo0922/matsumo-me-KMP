plugins {
    alias(libs.plugins.kmp.multiplatform).apply(false)
    alias(libs.plugins.kmp.compose).apply(false)
    alias(libs.plugins.kotlin.compose.compiler) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.libraries) apply false
    alias(libs.plugins.detekt) apply false
}
