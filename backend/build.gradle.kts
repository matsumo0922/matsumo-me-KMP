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
        project.rootDir.resolve("local.properties").takeIf { it.exists() }?.also {
            load(it.inputStream())
        }
    }

    mainClass.set("io.ktor.server.netty.EngineMain")
    applicationDefaultJvmArgs = listOfNotNull(
        localProperties.getJvmArg("SUPABASE_URL"),
        localProperties.getJvmArg("SUPABASE_KEY"),
        localProperties.getJvmArg("BACKEND_PORT"),
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

fun Properties.getJvmArg(key: String): String? {
    return get(key)?.let { "-D$key=${it}" }
}
