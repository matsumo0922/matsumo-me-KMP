package me.matsumo.blog.app.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.matsumo.blog.core.domain.Destinations
import me.matsumo.blog.core.theme.BindToNavigation
import me.matsumo.blog.feature.article.articlesScreen
import me.matsumo.blog.feature.home.homeScreen

@Composable
internal fun BlogNavHost(
    navController: NavHostController,
    startDestination: Destinations,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        homeScreen()
        articlesScreen()
    }

    BindToNavigation(navController)
}
