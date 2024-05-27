package me.matsumo.blog.app

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.matsumo.blog.core.model.UserData
import me.matsumo.blog.core.ui.theme.MMTheme
import org.koin.compose.KoinContext

@Composable
fun MMApp(
    userData: UserData,
    windowWidthSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    KoinContext {
        MMTheme(
            themeConfig = userData.themeConfig,
            themeColorConfig = userData.themeColorConfig,
            windowWidthSize = windowWidthSize,
        ) {

        }
    }
}