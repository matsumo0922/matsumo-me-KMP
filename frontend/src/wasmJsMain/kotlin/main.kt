import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.browser.document
import me.matsumo.blog.app.BlogApp
import me.matsumo.blog.initKoin
import org.koin.compose.KoinContext

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    Napier.base(DebugAntilog())

    ComposeViewport(document.body!!) {
        KoinContext {
            BlogApp()
        }
    }
}
