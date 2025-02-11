package me.matsumo.blog.feature.articledetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import me.matsumo.blog.core.domain.Destinations

fun NavGraphBuilder.articleDetailScreen() {
    composable<Destinations.ArticleDetail> {
        ArticleDetailRoute(
            modifier = Modifier.fillMaxSize(),
            articleId = it.toRoute<Destinations.ArticleDetail>().id
        )
    }
}
