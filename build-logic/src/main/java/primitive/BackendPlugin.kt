package primitive

import me.matsumo.blog.bundle
import me.matsumo.blog.library
import me.matsumo.blog.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class BackendPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("org.jetbrains.kotlin.jvm")
                apply("io.ktor.plugin")
                apply("kotlinx-serialization")
            }

            extensions.configure<KotlinJvmProjectExtension> {
                jvmToolchain(21)
            }

            dependencies {
                "implementation"(libs.bundle("ktor-server"))
                "implementation"(libs.library("supabase-postgrest"))
            }
        }
    }
}
