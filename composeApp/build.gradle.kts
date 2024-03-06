@file:Suppress("OPT_IN_USAGE")

plugins {
    alias(libs.plugins.kmp)
    alias(libs.plugins.kmpCompose)
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "matsumo-me.js"
            }
        }

        binaries.executable()
    }

    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.runtimeSaveable)
            implementation(compose.foundation)
            implementation(compose.animation)
            implementation(compose.animationGraphics)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
        }
    }
}

compose.experimental {
    web.application {}
}
