package me.matsumo.blog.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import me.matsumo.blog.core.ui.animation.NavigateAnimation
import me.matsumo.blog.feature.article.articleScreen
import me.matsumo.blog.feature.article.navigateToArticle
import me.matsumo.blog.feature.home.HomeRoute
import me.matsumo.blog.feature.home.homeScreen

@Composable
fun MMNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute,
        enterTransition = { NavigateAnimation.Home.enter },
        exitTransition = { NavigateAnimation.Home.exit },
    ) {
        homeScreen(
            navigateToArticleDetail = navController::navigateToArticle,
            navigateToTagDetail = {},
        )

        articleScreen()
    }
}
