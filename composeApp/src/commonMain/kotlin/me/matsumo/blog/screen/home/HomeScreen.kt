package me.matsumo.blog.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import me.matsumo.blog.core.model.Article
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.model.WindowWidthSize
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.theme.HEADER_HEIGHT
import me.matsumo.blog.core.theme.LocalWindowWidthSize
import me.matsumo.blog.core.ui.AsyncLoadContentsWithHeader
import me.matsumo.blog.core.ui.component.ArticleCard
import me.matsumo.blog.core.ui.component.FooterView
import me.matsumo.blog.core.ui.component.NonLazyGrid
import me.matsumo.blog.core.utils.log

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
        onSetThemeConfig = component::setThemeConfig,
    ) {
        HomeIdleScreen(
            modifier = Modifier.fillMaxSize(),
            articles = it.articles,
            onSetThemeConfig = component::setThemeConfig,
            onClickArticle = component::onNavigateToArticle,
            onClickTag = {},
            onClickAbout = component::onNavigateToAbout,
        )
    }
}

@Composable
fun HomeIdleScreen(
    articles: List<Article>,
    onSetThemeConfig: (ThemeConfig) -> Unit,
    onClickArticle: (String) -> Unit,
    onClickTag: (String) -> Unit,
    onClickAbout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowWidthSize = LocalWindowWidthSize.current
    val span by remember(windowWidthSize) {
        mutableStateOf(
            when (windowWidthSize) {
                is WindowWidthSize.Compact -> 1
                is WindowWidthSize.Medium -> 2
                is WindowWidthSize.Expanded -> 3
            }
        )
    }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(HEADER_HEIGHT)
        )

        NonLazyGrid(
            modifier = Modifier
                .widthIn(max = CONTAINER_MAX_WIDTH)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            columns = span,
            itemCount = articles.size,
        ) { index ->
            ArticleCard(
                article = articles[index],
                onClickArticle = { onClickArticle.invoke(it.id.toString()) },
                onClickTag = onClickTag,
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )

        FooterView(
            modifier = Modifier.fillMaxWidth(),
            onSetThemeConfig = onSetThemeConfig,
        )
    }
}
