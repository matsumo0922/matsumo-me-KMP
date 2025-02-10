package me.matsumo.blog.feature.articles

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.matsumo.blog.core.domain.Destinations

fun NavGraphBuilder.articlesScreen() {
    composable<Destinations.Articles> {
        ArticlesRoute(
            modifier = Modifier.fillMaxSize(),
        )
    }
}
