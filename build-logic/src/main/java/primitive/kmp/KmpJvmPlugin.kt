package primitive.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

@Suppress("unused")
class KmpJvmPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            kotlin {
                jvm()
            }
        }
    }
}
