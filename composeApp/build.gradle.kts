import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kmp.multiplatform)
    alias(libs.plugins.kmp.compose)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.libraries)
    alias(libs.plugins.detekt)
}

kotlin {
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
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(project.dependencies.platform(libs.kotlin.bom))
            implementation(project.dependencies.platform(libs.koin.bom))

            api(libs.bundles.infra.api)
            api(libs.bundles.ui.common.api)
            api(libs.bundles.koin)
            api(libs.bundles.ktor)

            
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
    }

    dependencies {
        // detekt
        "detektPlugins"(libs.detekt.formatting)
        "detektPlugins"(libs.twitter.compose.rule)
    }
}

compose.experimental {
    web.application {}
}
