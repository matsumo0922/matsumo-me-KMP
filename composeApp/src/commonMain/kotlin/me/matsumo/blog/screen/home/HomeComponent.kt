package me.matsumo.blog.screen.home

import com.arkivanov.decompose.ComponentContext

interface HomeComponent {
    fun onNavigateToAbout()
}

class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val navigateToAbout: () -> Unit
) : HomeComponent, ComponentContext by componentContext {

    override fun onNavigateToAbout() {
        navigateToAbout.invoke()
    }
}
