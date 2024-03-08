package me.matsumo.blog.screen.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.decompose.value.Value
import io.github.aakira.napier.Napier
import io.github.aakira.napier.log
import kotlinx.serialization.Serializable
import me.matsumo.blog.core.utils.currentUrl
import me.matsumo.blog.core.utils.log
import me.matsumo.blog.screen.about.AboutComponent
import me.matsumo.blog.screen.about.DefaultAboutComponent
import me.matsumo.blog.screen.home.DefaultHomeComponent
import me.matsumo.blog.screen.home.HomeComponent
import me.matsumo.blog.screen.splash.DefaultSplashComponent
import me.matsumo.blog.screen.splash.SplashComponent

@OptIn(ExperimentalDecomposeApi::class)
class DefaultRootComponent(
    private val componentContext: ComponentContext,
    private val webHistoryController: WebHistoryController
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Navigation>()

    private val stack = childStack(
        source = navigation,
        serializer = Navigation.serializer(),
        initialStack = { getInitialStack(webHistoryController.historyPaths) },
        childFactory = ::childFactory,
        handleBackButton = true,
    )

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = stack

    init {
        webHistoryController.attach(
            navigator = navigation,
            stack = stack,
            serializer = Navigation.serializer(),
            getPath = ::getPathForNavigation,
            getConfiguration = ::getNavigationForPath,
        )
    }

    private fun childFactory(navigation: Navigation, componentContext: ComponentContext): RootComponent.Child {
        return when (navigation) {
            Navigation.Splash -> RootComponent.Child.Splash(splashComponent(componentContext))
            Navigation.Home -> RootComponent.Child.Home(homeComponent(componentContext))
            Navigation.About -> RootComponent.Child.About(aboutComponent(componentContext))
        }
    }

    private fun getInitialStack(webHistoryPaths: List<String>?): List<Navigation> {
        log("Root", "getInitialStack: $webHistoryPaths")

        return if (webHistoryPaths.isNullOrEmpty()) {
            listOf(getNavigationForPath(currentUrl))
        } else {
            webHistoryPaths.map { getNavigationForPath(it) }
        }
    }

    private fun getNavigationForPath(path: String): Navigation {
        log("Root", "getNavigationForPath: $path")

        val pathElement = path.substringAfterLast("/")
        val navigation = WebPath.entries.find { it.path == pathElement }

        return navigation?.navigation ?: Navigation.Splash
    }

    private fun getPathForNavigation(navigation: Navigation): String {
        log("Root", "getPathForNavigation: $navigation")

        return "/" + (WebPath.entries.find { it.navigation == navigation }?.path ?: "")
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
            navigateToAbout = { navigation.pushToFront(Navigation.About) },
        )
    }

    private fun aboutComponent(componentContext: ComponentContext): AboutComponent {
        return DefaultAboutComponent(
            componentContext = componentContext,
            navigateToHome = { navigation.pushToFront(Navigation.Home) },
        )
    }

    @Serializable
    private sealed interface Navigation {

        @Serializable
        data object Splash : Navigation

        @Serializable
        data object Home : Navigation

        @Serializable
        data object About : Navigation
    }

    private enum class WebPath(
        val path: String,
        val navigation: Navigation,
    ) {
        SPLASH(
            path = "",
            navigation = Navigation.Splash,
        ),
        HOME(
            path = "home",
            navigation = Navigation.Home,
        ),
        ABOUT(
            path = "about",
            navigation = Navigation.About,
        ),
    }
}
