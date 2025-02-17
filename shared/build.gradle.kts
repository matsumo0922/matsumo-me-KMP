plugins {
    id("matsumo.primitive.kmp.common")
    id("matsumo.primitive.kmp.android.library")
    id("matsumo.primitive.kmp.android")
    id("matsumo.primitive.kmp.jvm")
    id("matsumo.primitive.ktorfit")
    id("matsumo.primitive.detekt")
}

android {
    namespace = "me.matsumo.blog.shared"
}

kotlin {
    wasmJs {
        useEsModules()
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.infra)
        }
    }
}
