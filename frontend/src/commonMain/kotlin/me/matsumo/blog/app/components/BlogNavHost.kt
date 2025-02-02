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
    modifier: Modifier = Modifier,
) {
    BindToNavigation(navController)

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destinations.Home,
    ) {
        homeScreen()
        articlesScreen()
    }
}
