package me.matsumo.blog.feature.articles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import me.matsumo.blog.core.domain.Destinations
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.theme.LocalDevice
import me.matsumo.blog.core.theme.rememberWindowWidthDp
import me.matsumo.blog.core.ui.AsyncLoadContents
import me.matsumo.blog.core.ui.BlogBottomBar
import me.matsumo.blog.core.ui.FixedWithEdgeSpace
import me.matsumo.blog.core.ui.itemsWithEdgeSpace
import me.matsumo.blog.feature.articles.components.ArticleCard
import me.matsumo.blog.shared.model.Article
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ArticlesRoute(
    navigateTo: (Destinations) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArticlesViewModel = koinViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
        retryAction = viewModel::fetch,
    ) { uiState ->
        ArticlesScreen(
            modifier = Modifier.fillMaxSize(),
            articles = uiState.articles.toImmutableList(),
            onArticleClicked = { navigateTo(Destinations.ArticleDetail(it)) },
        )
    }
}

@Composable
private fun ArticlesScreen(
    articles: ImmutableList<Article>,
    onArticleClicked: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val isMobile = LocalDevice.current == Device.MOBILE
    val windowWidth by rememberWindowWidthDp()

    val spanCount = if (isMobile) 1 else 3
    val arrangementSpace = if (isMobile) 24.dp else 32.dp
    val edgeSpace = (windowWidth - CONTAINER_MAX_WIDTH).coerceAtLeast(0.dp) / 2

    BoxWithConstraints(modifier) {
        val lazyGridState = rememberLazyGridState()
        var footerHeight by remember { mutableStateOf(0.dp) }
        val parentHeight = maxHeight

        val extraSpacerHeight by remember(lazyGridState, parentHeight, articles) {
            derivedStateOf {
                val layoutInfo = lazyGridState.layoutInfo
                val articleItems = layoutInfo.visibleItemsInfo.filter { item ->
                    item.key !in setOf("topSpacer", "middleSpacer", "footer")
                }

                if (articleItems.isEmpty() || articleItems.size != articles.size) {
                    0.dp
                } else {
                    val lastArticleBottomPx = articleItems.maxOf { it.offset.y + it.size.height }
                    val lastArticleBottomDp = with(density) { lastArticleBottomPx.toDp() }

                    (parentHeight - footerHeight - lastArticleBottomDp - 24.dp).coerceAtLeast(0.dp)
                }
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            state = lazyGridState,
            columns = FixedWithEdgeSpace(spanCount, edgeSpace),
            verticalArrangement = Arrangement.spacedBy(arrangementSpace),
            horizontalArrangement = Arrangement.spacedBy(arrangementSpace),
        ) {
            item(
                key = "topSpacer",
                span = { GridItemSpan(maxLineSpan) },
            ) {
                Spacer(
                    modifier = Modifier.height(8.dp),
                )
            }

            itemsWithEdgeSpace(
                spanCount = spanCount,
                items = articles,
                key = { it.id },
            ) {
                SelectionContainer {
                    ArticleCard(
                        modifier = Modifier.fillMaxWidth(),
                        article = it,
                        onClickArticle = onArticleClicked,
                    )
                }
            }

            item(
                key = "middleSpacer",
                span = { GridItemSpan(maxLineSpan) },
            ) {
                Spacer(
                    modifier = Modifier.height(extraSpacerHeight),
                )
            }

            item(
                key = "footer",
                span = { GridItemSpan(maxLineSpan) },
            ) {
                BlogBottomBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            footerHeight = with(density) { it.size.height.toDp() }
                        },
                )
            }
        }
    }
}
