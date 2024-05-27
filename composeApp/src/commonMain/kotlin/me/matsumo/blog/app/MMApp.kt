package me.matsumo.blog.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.matsumo.blog.core.model.UserData
import me.matsumo.blog.core.ui.AsyncLoadContents
import me.matsumo.blog.core.ui.theme.MMTheme
import me.matsumo.blog.core.ui.theme.shouldUseDarkTheme
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MMApp(
    modifier: Modifier = Modifier,
    viewModel: MMViewModel = koinViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val windowSize = calculateWindowSizeClass()

    AsyncLoadContents(
        modifier = modifier.fillMaxSize(),
        screenState = screenState,
    ) { uiState ->
        KoinContext {
            MMTheme(
                themeConfig = uiState.userData.themeConfig,
                themeColorConfig = uiState.userData.themeColorConfig,
                windowWidthSize = windowSize.widthSizeClass,
            ) {
                MMNavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}
