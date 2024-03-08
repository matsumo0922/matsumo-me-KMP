package me.matsumo.blog.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.matsumo.blog.core.theme.MMTheme
import me.matsumo.blog.core.theme.getCaskaydiaCoveNerdFontFamily
import me.matsumo.blog.core.ui.component.TypingText
import me.matsumo.blog.core.ui.component.buildTypeText

@Composable
internal fun SplashScreen(
    splashComponent: SplashComponent,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val consoleLogTextStyle = MaterialTheme.typography.headlineLarge.copy(Color(0xffeaeaea))

    var consoleLog by remember { mutableStateOf(AnnotatedString("")) }
    var isFinishTyping by remember { mutableStateOf(false) }

    LaunchedEffect(isFinishTyping) {
        if (isFinishTyping) {
            consoleLog = buildOutputStartUpStep.asConsoleLog(consoleLogTextStyle)

            delay(500)

            consoleLog = (consoleLog.text + "\n" + buildOutputConfigureStep).asConsoleLog(consoleLogTextStyle)

            delay(200)

            for (taskStep in buildOutputTaskStep.split("\n")) {
                consoleLog = (consoleLog.text + "\n" + taskStep).asConsoleLog(consoleLogTextStyle)
                delay(25)
            }

            delay(25)

            consoleLog = (consoleLog.text + "\n" + buildOutputSuccessStep).asConsoleLog(consoleLogTextStyle)

            delay(100)

            splashComponent.onNavigateToHome()
        }
    }

    LaunchedEffect(consoleLog) {
        listState.animateScrollToItem(2)
    }

    MMTheme(
        fonts = getCaskaydiaCoveNerdFontFamily(),
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(48.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    TypingText(
                        typeText = buildTypeText("$ ", 200, "./gradlew ", 150, "matsumo-me:", 300, "wasmJsBrowserRun", 800),
                        style = consoleLogTextStyle,
                        color = Color(0xffeaeaea),
                        onCompleted = { isFinishTyping = true }
                    )
                }

                item {
                    Text(
                        text = consoleLog,
                        style = consoleLogTextStyle,
                    )
                }
            }
        }
    }
}

private fun String.asConsoleLog(textStyle: TextStyle) = buildAnnotatedString {
    val green = Color(0xff00c135)
    val blue = Color(0xff1fc3c6)
    val pink = Color(0xffc03eb8)

    append(this@asConsoleLog)

    for (str in "Configure".toRegex().findAll(this@asConsoleLog)) {
        addStyle(
            style = textStyle.copy(pink).toSpanStyle(),
            start = str.range.first,
            end = str.range.last + 1,
        )
    }

    for (str in "Task|UP-TO-DATE".toRegex().findAll(this@asConsoleLog)) {
        addStyle(
            style = textStyle.copy(blue).toSpanStyle(),
            start = str.range.first,
            end = str.range.last + 1,
        )
    }

    for (str in "BUILD SUCCESSFUL".toRegex().findAll(this@asConsoleLog)) {
        addStyle(
            style = textStyle.copy(green).toSpanStyle(),
            start = str.range.first,
            end = str.range.last + 1,
        )
    }
}

private val buildOutputStartUpStep = """    
    Gradle Daemon started in 1 s 123 ms
    starting Gradle Daemon...
    
""".trimIndent()

private val buildOutputConfigureStep = """    
    > Configure project :matsumo-me
    New 'wasm' target is Work-in-Progress and is subject to change without notice.
    
""".trimIndent()

private val buildOutputTaskStep = """
    > Task :matsumo-me:checkKotlinGradlePluginConfigurationErrors
    > Task :matsumo-me:generateComposeResClass UP-TO-DATE
    > Task :kotlinNodeJsSetup UP-TO-DATE
    > Task :kotlinNpmCachesSetup
    > Task :kotlinRestoreYarnLock UP-TO-DATE
    > Task :kotlinYarnSetup UP-TO-DATE
    > Task :matsumo-me:jsPackageJson
    > Task :matsumo-me:jsTestPackageJson UP-TO-DATE
    > Task :matsumo-me:wasmJsPackageJson UP-TO-DATE
    > Task :matsumo-me:wasmJsTestPackageJson
    > Task :matsumo-me:jsPublicPackageJson UP-TO-DATE
    > Task :matsumo-me:jsTestPublicPackageJson
    > Task :matsumo-me:wasmJsPublicPackageJson
    > Task :matsumo-me:wasmJsTestPublicPackageJson
    > Task :matsumo-me:core:checkKotlinGradlePluginConfigurationErrors
    > Task :matsumo-me:core:generateComposeResClass UP-TO-DATE
    > Task :matsumo-me:core:jsPackageJson
    > Task :matsumo-me:core:jsTestPackageJson UP-TO-DATE
    > Task :matsumo-me:core:wasmJsPackageJson UP-TO-DATE
    > Task :matsumo-me:core:wasmJsTestPackageJson
    > Task :matsumo-me:core:jsPublicPackageJson UP-TO-DATE
    > Task :matsumo-me:core:jsTestPublicPackageJson
    > Task :matsumo-me:core:wasmJsPublicPackageJson
    > Task :matsumo-me:core:wasmJsTestPublicPackageJson
    > Task :matsumo-me:feature:checkKotlinGradlePluginConfigurationErrors
    > Task :matsumo-me:feature:generateComposeResClass UP-TO-DATE
    > Task :matsumo-me:feature:jsPackageJson
    > Task :matsumo-me:feature:jsTestPackageJson UP-TO-DATE
    > Task :matsumo-me:feature:wasmJsPackageJson UP-TO-DATE
    > Task :matsumo-me:feature:wasmJsTestPackageJson
    > Task :matsumo-me:feature:jsPublicPackageJson UP-TO-DATE
    > Task :matsumo-me:feature:jsTestPublicPackageJson
    > Task :matsumo-me:feature:wasmJsPublicPackageJson
    > Task :matsumo-me:feature:wasmJsTestPublicPackageJson
    > Task :packageJsonUmbrella UP-TO-DATE
    > Task :rootPackageJson UP-TO-DATE
    > Task :kotlinNpmInstall UP-TO-DATE
    > Task :kotlinStoreYarnLock UP-TO-DATE
    > Task :matsumo-me:core:unpackSkikoWasmRuntimeWasmJs UP-TO-DATE
    > Task :matsumo-me:core:wasmJsProcessResources UP-TO-DATE
    > Task :matsumo-me:core:compileKotlinWasmJs
    > Task :matsumo-me:core:wasmJsMainClasses
    > Task :matsumo-me:core:compileDevelopmentExecutableKotlinWasmJs
    > Task :matsumo-me:core:wasmJsDevelopmentExecutableCompileSync
    > Task :matsumo-me:feature:unpackSkikoWasmRuntimeWasmJs UP-TO-DATE
    > Task :matsumo-me:feature:wasmJsProcessResources UP-TO-DATE
    > Task :matsumo-me:feature:compileKotlinWasmJs
    > Task :matsumo-me:feature:wasmJsMainClasses
    > Task :matsumo-me:feature:compileDevelopmentExecutableKotlinWasmJs
    > Task :matsumo-me:feature:wasmJsDevelopmentExecutableCompileSync
    > Task :matsumo-me:unpackSkikoWasmRuntimeWasmJs UP-TO-DATE
    > Task :matsumo-me:wasmJsProcessResources UP-TO-DATE
    > Task :matsumo-me:compileKotlinWasmJs
    > Task :matsumo-me:wasmJsMainClasses
    > Task :matsumo-me:compileDevelopmentExecutableKotlinWasmJs
    > Task :matsumo-me:wasmJsDevelopmentExecutableCompileSync
    > Task :matsumo-me:wasmJsBrowserRun
    
""".trimIndent()

private val buildOutputSuccessStep = """
    BUILD SUCCESSFUL in 3s
    23 actionable tasks: 6 executed, 17 up-to-date
""".trimIndent()
