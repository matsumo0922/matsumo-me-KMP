package me.matsumo.blog

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

fun Project.androidApplication(action: ApplicationExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.setupAndroid() {
    androidApplication {
        defaultConfig {
            targetSdk = libs.version("targetSdk").toInt()
            minSdk = libs.version("minSdk").toInt()

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        splits {
            abi {
                isEnable = true
                isUniversalApk = true

                reset()
                include("x86", "x86_64", "armeabi-v7a", "arm64-v8a")
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
            isCoreLibraryDesugaringEnabled = true
        }

        dependencies {
            add("coreLibraryDesugaring", libs.library("desugar"))
        }
    }
}
