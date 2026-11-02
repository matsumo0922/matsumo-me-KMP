package primitive

import me.matsumo.blog.configureDetekt
import me.matsumo.blog.library
import me.matsumo.blog.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DetektPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("dev.detekt")

            configureDetekt()

            dependencies {
                "detektPlugins"(libs.library("detekt-ktlint-wrapper"))
                "detektPlugins"(libs.library("compose-rules-detekt"))
            }
        }
    }
}
