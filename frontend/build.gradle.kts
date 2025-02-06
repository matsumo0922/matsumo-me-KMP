import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    id("matsumo.primitive.kmp.common")
    id("matsumo.primitive.kmp.compose")
    id("matsumo.primitive.kmp.android.application")
    id("matsumo.primitive.kmp.android")
    id("matsumo.primitive.kmp.wasm")
    id("matsumo.primitive.ktorfit")
    id("matsumo.primitive.detekt")
}

android {
    namespace = "me.matsumo.blog"
}

kotlin {
    composeCompiler {
        featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.infra)
            implementation(libs.bundles.ui.common)
            implementation(libs.bundles.ktor)
            implementation(libs.bundles.koin)
            implementation(libs.bundles.coil)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.ktorfit)
        }

        androidMain.dependencies {
            implementation(libs.bundles.ui.android)
        }
    }
}
