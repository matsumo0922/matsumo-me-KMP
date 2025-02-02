package me.matsumo.blog.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.matsumo.blog.core.domain.Destinations

fun NavGraphBuilder.homeScreen() {
    composable<Destinations.Home> {
        HomeRoute(
            modifier = Modifier.fillMaxSize(),
        )
    }
}
