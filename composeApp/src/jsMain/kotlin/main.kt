import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.webhistory.DefaultWebHistoryController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import kotlinx.browser.window
import me.matsumo.blog.MMApp
import me.matsumo.blog.core.model.WindowWidthSize
import me.matsumo.blog.core.model.calculateWindowWidthSize
import me.matsumo.blog.screen.root.DefaultRootComponent
import org.jetbrains.skiko.wasm.onWasmReady
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

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
            val windowSize = LocalWindowInfo.current.containerSize.width
            var windowWidthSize: WindowWidthSize = remember { calculateWindowWidthSize(windowSize) }

            window.addEventListener("resize", object : EventListener {
                override fun handleEvent(event: Event) {
                    windowWidthSize = calculateWindowWidthSize(window.innerWidth)
                }
            })

            MMApp(
                modifier = Modifier.fillMaxSize(),
                component = root,
                windowWidthSize = windowWidthSize,
            )
        }
    }
}
