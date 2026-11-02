
import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.TargetConfigDsl
import java.util.Properties

plugins {
    id("matsumo.primitive.kmp.common")
    id("matsumo.primitive.kmp.compose")
    id("matsumo.primitive.kmp.android.library")
    id("matsumo.primitive.kmp.android")
    id("matsumo.primitive.kmp.ios")
    id("matsumo.primitive.kmp.wasm")
    id("matsumo.primitive.ktorfit")
    id("matsumo.primitive.detekt")
    alias(libs.plugins.build.konfig)
}

kotlin {
    android {
        namespace = "me.matsumo.blog.composeapp"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared"))

            implementation(libs.bundles.infra)
            implementation(libs.bundles.ui.common)
            implementation(libs.bundles.ktor.client)
            implementation(libs.bundles.koin.client)
            implementation(libs.bundles.coil)
            implementation(libs.bundles.markdown)

            implementation(libs.bundles.ui.compose)

            implementation(libs.ktorfit)
        }

        androidMain.dependencies {
            implementation(libs.bundles.ui.android)
            implementation(libs.ktor.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.darwin)
        }

        wasmJsMain.dependencies {
            implementation(libs.kotlinx.browser)
            implementation(libs.ktor.wasmJs)
        }
    }
}

buildkonfig {
    val localProperties = Properties().apply {
        project.rootDir.resolve("local.properties").takeIf { it.exists() }?.also {
            load(it.inputStream())
        }
    }

    packageName = "me.matsumo.blog"

    defaultConfigs {
        putBuildConfig(localProperties, "VERSION_NAME", libs.versions.versionName.get())
        putBuildConfig(localProperties, "VERSION_CODE", libs.versions.versionCode.get())
        putBuildConfig(localProperties, "BACKEND_URL", defaultValue = "http://localhost:9090/")
        putBuildConfig(localProperties, "REVISION", defaultValue = "UNKNOWN")
    }
}

fun TargetConfigDsl.putBuildConfig(
    localProperties: Properties,
    key: String,
    value: String? = null,
    defaultValue: String = "",
) {
    val property = localProperties.getProperty(key)
    val env = System.getenv(key)

    buildConfigField(FieldSpec.Type.STRING, key, (value ?: property ?: env ?: defaultValue).replace("\"", ""))
}
