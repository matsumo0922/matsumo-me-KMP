import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.webhistory.DefaultWebHistoryController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import me.matsumo.blog.MMApp
import me.matsumo.blog.core.repository.di.repositoryModule
import me.matsumo.blog.core.utils.initKoin
import me.matsumo.blog.screen.root.DefaultRootComponent
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class, ExperimentalDecomposeApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()
    val root = DefaultRootComponent(
        componentContext = DefaultComponentContext(lifecycle),
        webHistoryController = DefaultWebHistoryController(),
    )

    onWasmReady {
        lifecycle.resume()

        CanvasBasedWindow("matsumo-me") {
            MMApp(
                modifier = Modifier.fillMaxSize(),
                component = root,
            )
        }
    }
}
