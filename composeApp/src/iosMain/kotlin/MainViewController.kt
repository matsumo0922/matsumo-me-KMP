import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import me.matsumo.blog.app.BlogApp
import me.matsumo.blog.core.theme.getNotoSansJPFontFamily
import me.matsumo.blog.initKoin
import me.matsumo.blog.setupCoil
import org.koin.compose.KoinContext

@Suppress("FunctionNaming", "UnusedParameter")
fun MainViewController() = ComposeUIViewController {
    initKoin()
    setupCoil()

    KoinContext {
        BlogApp(
            modifier = Modifier.fillMaxSize(),
            fontFamily = getNotoSansJPFontFamily(),
        )
    }
}
