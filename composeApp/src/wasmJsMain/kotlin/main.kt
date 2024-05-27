import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import me.matsumo.blog.app.MMApp
import me.matsumo.blog.app.di.applyModules
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        applyModules()
    }

    CanvasBasedWindow("matsumo-me") {
        MMApp()
    }
}
