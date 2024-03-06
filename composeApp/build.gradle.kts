@file:Suppress("OPT_IN_USAGE")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kmp)
    alias(libs.plugins.kmpCompose)
    alias(libs.plugins.kotlin.serialization)
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
                languageVersion = "2.0"
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
            implementation(compose.components.uiToolingPreview)

            implementation(project.dependencies.platform(libs.kotlin.bom))
            implementation(project.dependencies.platform(libs.kotlin.wrapper.bom))
            implementation(project.dependencies.platform(libs.koin.bom))

            implementation(libs.bundles.infra)
            implementation(libs.bundles.ui.common)
            implementation(libs.bundles.koin)
            implementation(libs.bundles.ktor)
            implementation(libs.bundles.decompose)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
        }
    }
}

compose.experimental {
    web.application {}
}

tasks.withType(KotlinCompile::class.java) {
    compilerOptions.freeCompilerArgs.addAll(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:experimentalStrongSkipping=true",
    )
}
