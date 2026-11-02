package me.matsumo.blog

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.extensions.DetektExtension
import dev.detekt.gradle.report.ReportMergeTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

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
    // 型情報ありの full モードのタスク（detektMain など）は source set ごとのソースで解析するため、ここでは触らない
    val detekt = tasks.named<Detekt>("detekt") {
        finalizedBy(reportMerge)
        setSource(detektSources())
    }

    reportMerge.configure {
        input.from(detekt.flatMap { it.reports.checkstyle.outputLocation })
    }
}

private fun Project.detektSources() = if (this == rootProject) {
    // root project はアプリ以外の Kotlin（build-logic と root の Gradle スクリプト）を解析する
    files("build-logic/src", "build-logic/build.gradle.kts", "build-logic/settings.gradle.kts", "build.gradle.kts", "settings.gradle.kts")
} else {
    fileTree(projectDir) {
        include("**/*.kt", "**/*.kts")
        exclude("**/resources/**", "**/build/**")
    }
}
