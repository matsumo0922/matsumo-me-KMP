package me.matsumo.blog.app.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.matsumo.blog.core.domain.Destinations
import me.matsumo.blog.core.theme.BindToNavigation
import me.matsumo.blog.feature.application.privacy.privacyPolicyScreen
import me.matsumo.blog.feature.application.team.teamOfServiceScreen
import me.matsumo.blog.feature.articledetail.articleDetailScreen
import me.matsumo.blog.feature.articles.articlesScreen
import me.matsumo.blog.feature.home.homeScreen
import me.matsumo.blog.feature.revision.revisionScreen

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

        articlesScreen(
            navigateTo = navController::navigate,
        )

        articleDetailScreen(
            terminate = { navController.popBackStack<Destinations.Articles>(false, saveState = false) },
        )
        privacyPolicyScreen()
        teamOfServiceScreen()
        revisionScreen()
    }

    BindToNavigation(navController)
}
