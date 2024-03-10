package me.matsumo.blog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import me.matsumo.blog.core.model.WindowWidthSize
import me.matsumo.blog.core.theme.MMTheme
import me.matsumo.blog.screen.about.AboutComponent
import me.matsumo.blog.screen.about.AboutScreen
import me.matsumo.blog.screen.article.ArticleScreen
import me.matsumo.blog.screen.home.HomeComponent
import me.matsumo.blog.screen.home.HomeScreen
import me.matsumo.blog.screen.root.RootComponent
import me.matsumo.blog.screen.splash.SplashScreen

@Composable
internal fun MMApp(
    component: RootComponent,
    windowWidthSize: WindowWidthSize,
    modifier: Modifier = Modifier,
) {
    val themeConfig by component.themeConfig.subscribeAsState()

    MMTheme(
        themeConfig = themeConfig,
        windowWidthSize = windowWidthSize,
    ) {
        Children(
            modifier = modifier.background(MaterialTheme.colorScheme.surface),
            stack = component.childStack,
            animation = stackAnimation(fade())
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.Splash -> SplashScreen(child.component)
                is RootComponent.Child.Home -> HomeScreen(child.component)
                is RootComponent.Child.About -> AboutScreen(child.component)
                is RootComponent.Child.Article -> ArticleScreen(child.component, "")
            }
        }
    }
}
