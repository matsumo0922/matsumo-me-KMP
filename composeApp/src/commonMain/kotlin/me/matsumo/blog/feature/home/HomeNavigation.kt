package me.matsumo.blog.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HomeRoute = "home"

fun NavController.navigateToHome() {
    this.navigate(HomeRoute)
}

fun NavGraphBuilder.homeScreen(
    navigateToArticleDetail: (String) -> Unit,
    navigateToTagDetail: (String) -> Unit,
) {
    composable(HomeRoute) {
        HomeRoute(
            modifier = Modifier.fillMaxSize(),
            navigateToArticleDetail = navigateToArticleDetail,
            navigateToTagDetail = navigateToTagDetail,
        )
    }
}
