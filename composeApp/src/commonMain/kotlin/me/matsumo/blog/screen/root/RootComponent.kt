package me.matsumo.blog.screen.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.screen.about.AboutComponent
import me.matsumo.blog.screen.article.ArticleComponent
import me.matsumo.blog.screen.home.HomeComponent
import me.matsumo.blog.screen.splash.SplashComponent

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    val themeConfig: Value<ThemeConfig>

    fun setThemeConfig(themeConfig: ThemeConfig)

    sealed class Child {
        class Splash(val component: SplashComponent) : Child()
        class Home(val component: HomeComponent) : Child()
        class About(val component: AboutComponent) : Child()
        class Article(val component: ArticleComponent) : Child()
    }
}
