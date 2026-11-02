package primitive.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpAndroidLibrary : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.kotlin.multiplatform.library")
                apply("kotlin-parcelize")
                apply("kotlinx-serialization")
            }
        }
    }
}
