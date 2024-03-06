package me.matsumo.blog.screen.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import me.matsumo.blog.screen.about.AboutComponent
import me.matsumo.blog.screen.home.HomeComponent

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Home(val component: HomeComponent) : Child()
        class About(val component: AboutComponent) : Child()
    }
}
