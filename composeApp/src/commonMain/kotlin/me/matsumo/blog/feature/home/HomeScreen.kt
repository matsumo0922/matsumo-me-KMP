package me.matsumo.blog.feature.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import me.matsumo.blog.core.model.Article
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.model.UserData
import me.matsumo.blog.core.ui.AsyncLoadContentsWithHeader
import me.matsumo.blog.core.ui.components.ArticleCard
import me.matsumo.blog.core.ui.components.FixedWithEdgeSpace
import me.matsumo.blog.core.ui.components.FooterView
import me.matsumo.blog.core.ui.components.itemsWithEdgeSpace
import me.matsumo.blog.core.ui.extensions.rememberArticleEdgeSpace
import me.matsumo.blog.core.ui.extensions.toDp
import me.matsumo.blog.core.ui.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.ui.theme.HEADER_HEIGHT
import me.matsumo.blog.core.ui.theme.LocalWindowWidthSize
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        if (screenState !is ScreenState.Idle) {
            viewModel.fetch()
        }
    }

    AsyncLoadContentsWithHeader(
        modifier = Modifier.fillMaxSize(),
        screenState = screenState,
        retryAction = viewModel::fetch,
        onSetThemeConfig = viewModel::setThemeConfig,
    ) {
        HomeScreen(
            modifier = modifier.fillMaxSize(),
            userData = it.userData,
            articles = it.articles.toImmutableList(),
            setThemeConfig = viewModel::setThemeConfig,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HomeScreen(
    userData: UserData,
    articles: ImmutableList<Article>,
    setThemeConfig: (ThemeConfig) -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowWidthSizeClass = LocalWindowWidthSize.current

    val space by rememberArticleEdgeSpace()
    val span by remember(windowWidthSizeClass) {
        mutableStateOf(
            when (windowWidthSizeClass) {
                WindowWidthSizeClass.Compact -> 1
                WindowWidthSizeClass.Medium -> 2
                WindowWidthSizeClass.Expanded -> 3
                else -> 1
            }
        )
    }

    LazyVerticalGrid(
        modifier = modifier,
        columns = FixedWithEdgeSpace(
            count = span,
            edgeSpace = space,
        ),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(
                modifier = Modifier.height(HEADER_HEIGHT + 16.dp)
            )
        }

        itemsWithEdgeSpace(
            spanCount = span,
            items = articles,
            key = { it.id },
        ) {
            ArticleCard(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                article = it,
                onClickArticle = {},
                onClickTag = {},
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            FooterView(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                onSetThemeConfig = setThemeConfig,
            )
        }
    }
}
