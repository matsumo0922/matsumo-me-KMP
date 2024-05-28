package me.matsumo.blog.feature.article

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val ArticleIdKey = "articleId"
const val ArticleRoute = "article/{$ArticleIdKey}"

fun NavController.navigateToArticle(id: String) {
    this.navigate("article/$id")
}

fun NavGraphBuilder.articleScreen() {
    composable(
        route = ArticleRoute,
        arguments = listOf(
            navArgument(ArticleIdKey) {
                type = NavType.StringType
            }
        )
    ) {
        ArticleRoute(
            modifier = Modifier.fillMaxSize(),
            articleId = it.arguments?.getString(ArticleIdKey).orEmpty(),
        )
    }
}