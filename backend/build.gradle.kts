plugins {
    id("matsumo.primitive.backend")
    id("matsumo.primitive.detekt")
}

group = "me.matsumo.blog"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${project.ext.has("development")}")
}

dependencies {
    implementation(project(":shared"))

    implementation(libs.bundles.infra)
    implementation(libs.bundles.koin.server)
}
