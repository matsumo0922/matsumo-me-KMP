package me.matsumo.blog.screen.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import me.matsumo.blog.screen.about.AboutComponent
import me.matsumo.blog.screen.about.DefaultAboutComponent
import me.matsumo.blog.screen.home.DefaultHomeComponent
import me.matsumo.blog.screen.home.HomeComponent

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
            Navigation.Home -> RootComponent.Child.Home(homeComponent(componentContext))
            Navigation.About -> RootComponent.Child.About(aboutComponent(componentContext))
        }
    }

    private fun getInitialStack(webHistoryPaths: List<String>?): List<Navigation> {
        return webHistoryPaths?.takeUnless(List<*>::isEmpty)?.map(::getNavigationForPath) ?: listOf(Navigation.Home)
    }

    private fun getNavigationForPath(path: String): Navigation {
        return when (path.removePrefix("/")) {
            WebPath.HOME -> Navigation.Home
            WebPath.ABOUT -> Navigation.About
            else -> throw IllegalArgumentException("Unknown path: $path")
        }
    }

    private fun getPathForNavigation(navigation: Navigation): String {
        return when (navigation) {
            Navigation.Home -> "/${WebPath.HOME}"
            Navigation.About -> "/${WebPath.ABOUT}"
        }
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
        data object Home : Navigation

        @Serializable
        data object About : Navigation
    }

    private object WebPath {
        const val HOME = "home"
        const val ABOUT = "about"
    }
}
