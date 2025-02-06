package primitive.kmp

import me.matsumo.blog.androidTestImplementation
import me.matsumo.blog.debugImplementation
import me.matsumo.blog.implementation
import me.matsumo.blog.library
import me.matsumo.blog.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KmpComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.compose")
            }
        }
    }
}
