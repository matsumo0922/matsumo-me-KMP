import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import me.matsumo.blog.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("matsumo-me") {
        App()
    }
}
