package me.matsumo.blog.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import me.matsumo.blog.core.model.Article
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.theme.HEADER_HEIGHT
import me.matsumo.blog.core.ui.AsyncLoadContentsWithHeader
import me.matsumo.blog.core.ui.component.ArticleCard

@Composable
fun HomeScreen(
    component: HomeComponent,
    modifier: Modifier = Modifier,
) {
    val screenState by component.screenState.collectAsState()

    LaunchedEffect(Unit) {
        if (screenState !is ScreenState.Idle) {
            component.fetch()
        }
    }

    AsyncLoadContentsWithHeader(
        modifier = modifier.fillMaxSize(),
        screenState = screenState,
    ) {
        HomeIdleScreen(
            modifier = Modifier
                .widthIn(max = CONTAINER_MAX_WIDTH)
                .fillMaxHeight(),
            articles = it.articles,
            onClickArticle = {},
            onClickTag = {},
            onClickAbout = component::onNavigateToAbout,
        )
    }
}

@Composable
fun HomeIdleScreen(
    articles: List<Article>,
    onClickArticle: (Article) -> Unit,
    onClickTag: (String) -> Unit,
    onClickAbout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(top = HEADER_HEIGHT, bottom = 24.dp)
    ) {
        items(
            items = articles,
            key = { it.id },
        ) {
            ArticleCard(
                article = it,
                onClickArticle = onClickArticle,
                onClickTag = onClickTag,
            )
        }
    }
}
