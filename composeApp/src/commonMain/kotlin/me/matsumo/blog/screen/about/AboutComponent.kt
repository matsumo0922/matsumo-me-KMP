package me.matsumo.blog.screen.about

import com.arkivanov.decompose.ComponentContext

interface AboutComponent {
    fun onNavigateToHome()
}

class DefaultAboutComponent(
    componentContext: ComponentContext,
    private val navigateToHome: () -> Unit
) : AboutComponent, ComponentContext by componentContext {

    override fun onNavigateToHome() {
        navigateToHome.invoke()
    }
}
