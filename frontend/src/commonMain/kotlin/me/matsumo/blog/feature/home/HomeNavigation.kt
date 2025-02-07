package me.matsumo.blog.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import me.matsumo.blog.core.domain.Destinations

fun NavGraphBuilder.homeScreen() {
    composable<Destinations.Home>(
        deepLinks = listOf(
            navDeepLink<Destinations.Home>("http://localhost:8080/home"),
            navDeepLink<Destinations.Home>("https://matsumo.me/home"),
        ),
    ) {
        HomeRoute(
            modifier = Modifier.fillMaxSize(),
        )
    }
}
