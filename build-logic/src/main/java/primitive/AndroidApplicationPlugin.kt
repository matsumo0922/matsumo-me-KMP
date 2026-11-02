package primitive

import me.matsumo.blog.androidApplication
import me.matsumo.blog.libs
import me.matsumo.blog.setupAndroid
import me.matsumo.blog.version
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("project-report")
            }

            androidApplication {
                setupAndroid()

                compileSdk = libs.version("compileSdk").toInt()
                defaultConfig.targetSdk = libs.version("targetSdk").toInt()
                buildFeatures.compose = true

                defaultConfig {
                    applicationId = "me.matsumo.blog"

                    versionName = libs.version("versionName")
                    versionCode = libs.version("versionCode").toInt()
                }

                packaging {
                    resources.excludes.addAll(
                        listOf(
                            "LICENSE",
                            "LICENSE.txt",
                            "NOTICE",
                            "asm-license.txt",
                            "cglib-license.txt",
                            "mozilla/public-suffix-list.txt",
                        )
                    )
                }
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }
    }
}
