import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("matsumo.primitive.kmp.common")
    id("matsumo.primitive.kmp.compose")
    id("matsumo.primitive.kmp.wasm")
    id("matsumo.primitive.detekt")
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        binaries.executable()
    }

    sourceSets {
        wasmJsMain.dependencies {
            implementation(project(":composeApp"))

            implementation(libs.bundles.infra)
            implementation(libs.kotlinx.browser)
            implementation(libs.ktor.core)
            implementation(libs.koin.compose)

            implementation(libs.kmp.compose.runtime)
            implementation(libs.kmp.compose.foundation)
            implementation(libs.kmp.compose.ui)
            implementation(libs.kmp.compose.material3)
        }
    }
}
