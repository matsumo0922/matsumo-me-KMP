plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.room.gradlePlugin)
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
        register("KmpAndroidApplication") {
            id = "matsumo.primitive.kmp.android.application"
            implementationClass = "primitive.kmp.KmpAndroidApplication"
        }
        register("KmpAndroidLibrary") {
            id = "matsumo.primitive.kmp.android.library"
            implementationClass = "primitive.kmp.KmpAndroidLibrary"
        }
        register("KmpWasmPlugin") {
            id = "matsumo.primitive.kmp.wasm"
            implementationClass = "primitive.kmp.KmpWasmPlugin"
        }
        register("KmpComposeCompose") {
            id = "matsumo.primitive.kmp.compose"
            implementationClass = "primitive.kmp.KmpComposePlugin"
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
