import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id("matsumo.primitive.kmp.common")
    id("matsumo.primitive.kmp.compose")
    id("matsumo.primitive.ktorfit")
    id("matsumo.primitive.detekt")
}

kotlin {
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.infra)
            implementation(libs.bundles.ui.common)
            implementation(libs.bundles.ktor)
            implementation(libs.bundles.koin)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
    }
}
