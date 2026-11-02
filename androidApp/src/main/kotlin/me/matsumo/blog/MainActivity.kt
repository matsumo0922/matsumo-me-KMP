package me.matsumo.blog

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import me.matsumo.blog.app.BlogApp
import me.matsumo.blog.app.BlogViewModel
import me.matsumo.blog.core.domain.isDark
import org.koin.compose.KoinContext

class MainActivity : FragmentActivity() {

    private val viewModel by viewModels<BlogViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KoinContext {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val isDark = uiState.theme.isDark()

                val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
                val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)

                DisposableEffect(isDark) {
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { isDark },
                        navigationBarStyle = SystemBarStyle.auto(lightScrim, darkScrim) { isDark },
                    )

                    onDispose {}
                }

                BlogApp(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = viewModel,
                )
            }
        }

        splashScreen.setKeepOnScreenCondition { false }
    }
}
