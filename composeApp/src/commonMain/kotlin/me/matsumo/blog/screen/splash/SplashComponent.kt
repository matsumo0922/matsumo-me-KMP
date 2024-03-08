package me.matsumo.blog.screen.splash

import com.arkivanov.decompose.ComponentContext

interface SplashComponent {
    fun onNavigateToHome()
}

class DefaultSplashComponent(
    componentContext: ComponentContext,
    private val navigateToHome: () -> Unit
) : SplashComponent, ComponentContext by componentContext {

    override fun onNavigateToHome() {
        navigateToHome.invoke()
    }
}
