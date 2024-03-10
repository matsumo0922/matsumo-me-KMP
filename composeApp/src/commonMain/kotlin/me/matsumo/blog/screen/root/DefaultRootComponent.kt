package me.matsumo.blog.screen.root

import coil3.toUri
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.github.aakira.napier.Napier
import io.github.aakira.napier.log
import io.ktor.http.*
import kotlinx.serialization.Serializable
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.utils.currentUrl
import me.matsumo.blog.core.utils.initKoin
import me.matsumo.blog.core.utils.initKoinIfNeeded
import me.matsumo.blog.core.utils.log
import me.matsumo.blog.screen.about.AboutComponent
import me.matsumo.blog.screen.about.DefaultAboutComponent
import me.matsumo.blog.screen.article.ArticleComponent
import me.matsumo.blog.screen.article.DefaultArticleComponent
import me.matsumo.blog.screen.home.DefaultHomeComponent
import me.matsumo.blog.screen.home.HomeComponent
import me.matsumo.blog.screen.splash.DefaultSplashComponent
import me.matsumo.blog.screen.splash.SplashComponent
import kotlin.reflect.KClass

@OptIn(ExperimentalDecomposeApi::class)
class DefaultRootComponent(
    private val componentContext: ComponentContext,
    private val webHistoryController: WebHistoryController
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Navigation>()

    private val routing = ClientRouter.routing {
        get("/home") {
            Navigation.Home
        }
        get("/about") {
            Navigation.About
        }
        get("/article/{id}") {
            Navigation.Article(
                id = it.pathParameters["id"]!!.toString()
            )
        }
    }

    private val stack = childStack(
        source = navigation,
        serializer = Navigation.serializer(),
        initialStack = { getInitialStack(webHistoryController.historyPaths) },
        childFactory = ::childFactory,
        handleBackButton = true,
    )

    private val _themeConfig = MutableValue(ThemeConfig.System)

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = stack

    override val themeConfig: Value<ThemeConfig> = _themeConfig

    init {
        webHistoryController.attach(
            navigator = navigation,
            stack = stack,
            serializer = Navigation.serializer(),
            getPath = ::getPathForNavigation,
            getConfiguration = ::getNavigationForPath,
        )
    }

    override fun setThemeConfig(themeConfig: ThemeConfig) {
        _themeConfig.value = themeConfig
    }

    private fun childFactory(navigation: Navigation, componentContext: ComponentContext): RootComponent.Child {
        return when (navigation) {
            is Navigation.Splash -> RootComponent.Child.Splash(splashComponent(componentContext))
            is Navigation.Home -> RootComponent.Child.Home(homeComponent(componentContext))
            is Navigation.About -> RootComponent.Child.About(aboutComponent(componentContext))
            is Navigation.Article -> RootComponent.Child.Article(articleComponent(componentContext))
        }
    }

    private fun getInitialStack(webHistoryPaths: List<String>?): List<Navigation> {
        initKoinIfNeeded()

        return if (webHistoryPaths.isNullOrEmpty()) {
            listOf(getNavigationForPath(currentUrl))
        } else {
            webHistoryPaths.map { getNavigationForPath(it) }
        }
    }

    private fun getNavigationForPath(path: String): Navigation {
        return routing.parse(Url(path)) ?: Navigation.Splash
    }

    private fun getPathForNavigation(navigation: Navigation): String {
        return "/" + navigation::class.simpleName?.lowercase()
    }

    private fun splashComponent(componentContext: ComponentContext): SplashComponent {
        return DefaultSplashComponent(
            componentContext = componentContext,
            navigateToHome = { navigation.pushToFront(Navigation.Home) },
        )
    }

    private fun homeComponent(componentContext: ComponentContext): HomeComponent {
        return DefaultHomeComponent(
            componentContext = componentContext,
            setThemeConfig = ::setThemeConfig,
            navigateToAbout = { navigation.pushToFront(Navigation.About) },
        )
    }

    private fun aboutComponent(componentContext: ComponentContext): AboutComponent {
        return DefaultAboutComponent(
            componentContext = componentContext,
            navigateToHome = { navigation.pushToFront(Navigation.Home) },
        )
    }

    private fun articleComponent(componentContext: ComponentContext): ArticleComponent {
        return DefaultArticleComponent(
            componentContext = componentContext,
            setThemeConfig = ::setThemeConfig,
        )
    }

    @Serializable
    sealed interface Navigation {

        @Serializable
        data object Splash : Navigation

        @Serializable
        data object Home : Navigation

        @Serializable
        data object About : Navigation

        @Serializable
        data class Article(val id: String) : Navigation
    }
}
