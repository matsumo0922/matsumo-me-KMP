import com.android.build.gradle.internal.tasks.factory.dependsOn
import java.util.*

plugins {
    id("matsumo.primitive.backend")
    id("matsumo.primitive.detekt")
}

group = "me.matsumo.blog.backend"
version = "0.0.1"

application {
    val localProperties = Properties().apply {
        if (project.rootDir.resolve("local.properties").exists()) {
            load(project.rootDir.resolve("local.properties").inputStream())
        }
    }

    mainClass.set("io.ktor.server.netty.EngineMain")
    applicationDefaultJvmArgs = listOf(
        localProperties.getJvmArg("SUPABASE_URL"),
        localProperties.getJvmArg("SUPABASE_KEY"),
        "-DPORT=9090"
    )
}

dependencies {
    implementation(project(":shared"))

    implementation(libs.bundles.infra)
    implementation(libs.bundles.koin.server)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.commonmark)

    implementation(libs.ksoup)
}

tasks {
    register("stage").dependsOn("installDist")
}

fun Properties.getJvmArg(key: String): String {
    return "-D$key=${get(key)}"
}
