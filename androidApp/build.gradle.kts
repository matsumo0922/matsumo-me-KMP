import com.android.build.api.variant.ResValue
import java.util.Properties

plugins {
    id("matsumo.primitive.android.application")
    id("matsumo.primitive.detekt")
}

android {
    namespace = "me.matsumo.blog"

    val localProperties = Properties().apply {
        project.rootDir.resolve("local.properties").takeIf { it.exists() }?.also {
            load(it.inputStream())
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("${project.rootDir}/gradle/keystore/debug.keystore")
        }
        create("release") {
            storeFile = file("${project.rootDir}/gradle/keystore/release.keystore")
            storePassword = localProperties.getProperty("storePassword") ?: System.getenv("RELEASE_STORE_PASSWORD")
            keyPassword = localProperties.getProperty("keyPassword") ?: System.getenv("RELEASE_KEY_PASSWORD")
            keyAlias = localProperties.getProperty("keyAlias") ?: System.getenv("RELEASE_KEY_ALIAS")
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
            versionNameSuffix = ".D"
            applicationIdSuffix = ".debug"
        }
    }

    packaging {
        jniLibs.useLegacyPackaging = true
    }

    androidComponents {
        onVariants {
            val appName = when (it.buildType) {
                "debug" -> "matsumo-me Debug"
                else -> "matsumo-me"
            }

            it.resValues.apply {
                put(it.makeResValueKey("string", "app_name"), ResValue(appName, null))
            }

            if (it.buildType == "release") {
                it.packaging.resources.excludes.add("META-INF/**")
            }
        }
    }
}

dependencies {
    implementation(project(":composeApp"))

    implementation(libs.bundles.ui.android)
    implementation(libs.kmp.compose.foundation)
    implementation(libs.kmp.lifecycle.runtime.compose)
    implementation(libs.kmp.lifecycle.viewmodel.compose)
    implementation(libs.koin.compose)
    implementation(libs.napier)
}
