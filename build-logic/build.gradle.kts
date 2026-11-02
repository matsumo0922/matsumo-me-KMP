plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.secret.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.build.konfig.gradlePlugin)
}

gradlePlugin {
    plugins {
        // KMP
        register("KmpPlugin") {
            id = "matsumo.primitive.kmp.common"
            implementationClass = "primitive.kmp.KmpCommonPlugin"
        }
        register("KmpAndroidPlugin") {
            id = "matsumo.primitive.kmp.android"
            implementationClass = "primitive.kmp.KmpAndroidPlugin"
        }
        register("KmpAndroidLibrary") {
            id = "matsumo.primitive.kmp.android.library"
            implementationClass = "primitive.kmp.KmpAndroidLibrary"
        }
        register("KmpIosPlugin") {
            id = "matsumo.primitive.kmp.ios"
            implementationClass = "primitive.kmp.KmpIosPlugin"
        }
        register("KmpWasmPlugin") {
            id = "matsumo.primitive.kmp.wasm"
            implementationClass = "primitive.kmp.KmpWasmPlugin"
        }
        register("KmpJvmPlugin") {
            id = "matsumo.primitive.kmp.jvm"
            implementationClass = "primitive.kmp.KmpJvmPlugin"
        }
        register("KmpComposeCompose") {
            id = "matsumo.primitive.kmp.compose"
            implementationClass = "primitive.kmp.KmpComposePlugin"
        }

        // Android
        register("AndroidApplicationPlugin") {
            id = "matsumo.primitive.android.application"
            implementationClass = "primitive.AndroidApplicationPlugin"
        }

        // Libraries
        register("DetektPlugin") {
            id = "matsumo.primitive.detekt"
            implementationClass = "primitive.DetektPlugin"
        }
        register("KtorfitPlugin") {
            id = "matsumo.primitive.ktorfit"
            implementationClass = "primitive.KtrofitPlugin"
        }
        register("BackendPlugin") {
            id = "matsumo.primitive.backend"
            implementationClass = "primitive.BackendPlugin"
        }
    }
}
