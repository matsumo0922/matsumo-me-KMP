package me.matsumo.blog

import androidx.compose.foundation.layout.fillMaxSize
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

@Composable
internal fun MMApp(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    MMTheme {
        Children(
            modifier = modifier,
            stack = component.childStack,
            animation = stackAnimation(fade() + scale())
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.Home -> HomeScreen(child.component)
                is RootComponent.Child.About -> AboutScreen(child.component)
            }
        }
    }
}
