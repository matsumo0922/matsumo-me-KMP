package me.matsumo.blog

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.extensions.DetektExtension
import dev.detekt.gradle.report.ReportMergeTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

internal fun Project.configureDetekt() {
    extensions.getByType<DetektExtension>().apply {
        toolVersion.set(libs.version("detekt"))
        // 並列処理
        parallel.set(true)
        // Detektの設定ファイル
        config.setFrom(files("${project.rootDir}/config/detekt/detekt.yml"))
        // デフォルト設定の上に自分の設定ファイルを適用する
        buildUponDefaultConfig.set(true)
        // ルール違反があった場合にfailさせない
        ignoreFailures.set(false)
        // ルール違反の自動修正を試みる
        autoCorrect.set(false)
    }

    // 各モジュールでの Detekt 実行結果を一つにまとめるタスク（CIでコメントする際に使用）
    // FYI: https://detekt.dev/docs/introduction/reporting/#kotlin-dsl
    val reportMerge: TaskProvider<ReportMergeTask> = if (!rootProject.tasks.names.contains("reportMerge")) {
        rootProject.tasks.register<ReportMergeTask>("reportMerge") {
            output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.xml"))
        }
    } else {
        rootProject.tasks.named<ReportMergeTask>("reportMerge")
    }

    // 型情報なしで解析する light モードの detekt タスクだけ、ソースを独自に指定してレポートを集める。
    // 型情報ありの full モードのタスク（detektMain など）は source set ごとのソースで解析するため、ソースは指定しない
    val detekt = tasks.named<Detekt>("detekt") {
        finalizedBy(reportMerge)
        setSource(detektSources())
    }

    reportMerge.configure {
        input.from(detekt.flatMap { it.reports.checkstyle.outputLocation })
    }

    // source set には KSP や Compose Resources の生成コードも含まれるため、build 配下を解析対象から外す
    val buildDir = layout.buildDirectory.get().asFile
    val fullModeTasks = tasks.withType<Detekt>().matching { it.isFullMode() }

    fullModeTasks.configureEach {
        exclude { it.file.startsWith(buildDir) }
    }

    // 型情報が必要なルール（UnusedPrivateFunction など）は full モードでしか実行されないため、まとめて実行するタスクを用意する
    val detektFull = tasks.register("detektFull") {
        group = "verification"
        description = "Runs detekt analysis with type resolution."
        dependsOn(fullModeTasks)
    }

    tasks.matching { it.name == "check" }.configureEach {
        dependsOn(detektFull)
    }
}

// light モードは detekt タスクと source set ごとの detekt<SourceSet>SourceSet タスク。それ以外が型情報ありの full モード
private fun Detekt.isFullMode(): Boolean = name != "detekt" && !name.endsWith("SourceSet")

private fun Project.detektSources() = if (this == rootProject) {
    // root project はアプリ以外の Kotlin（build-logic と root の Gradle スクリプト）を解析する
    files("build-logic/src", "build-logic/build.gradle.kts", "build-logic/settings.gradle.kts", "build.gradle.kts", "settings.gradle.kts")
} else {
    fileTree(projectDir) {
        include("**/*.kt", "**/*.kts")
        exclude("**/resources/**", "**/build/**")
    }
}
