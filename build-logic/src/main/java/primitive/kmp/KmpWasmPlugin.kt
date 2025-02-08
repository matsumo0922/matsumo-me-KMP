package primitive.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

@OptIn(ExperimentalWasmDsl::class)
@Suppress("unused")
class KmpWasmPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            kotlin {
                js {
                    browser()
                    binaries.executable()
                }

                wasmJs {
                    browser()
                    binaries.executable()
                }
            }
        }
    }
}
