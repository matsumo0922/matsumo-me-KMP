package me.matsumo.blog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import me.matsumo.blog.core.theme.MMTheme
import me.matsumo.blog.screen.about.AboutComponent
import me.matsumo.blog.screen.about.AboutScreen
import me.matsumo.blog.screen.home.HomeComponent
import me.matsumo.blog.screen.home.HomeScreen
import me.matsumo.blog.screen.root.RootComponent
import me.matsumo.blog.screen.splash.SplashScreen

@Composable
internal fun MMApp(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    MMTheme {
        Children(
            modifier = modifier.background(MaterialTheme.colorScheme.surface),
            stack = component.childStack,
            animation = stackAnimation(fade())
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.Splash -> SplashScreen(child.component)
                is RootComponent.Child.Home -> HomeScreen(child.component)
                is RootComponent.Child.About -> AboutScreen(child.component)
            }
        }
    }
}
