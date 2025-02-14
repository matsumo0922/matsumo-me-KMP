package me.matsumo.blog.feature.articledetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.toImmutableList
import me.matsumo.blog.shared.model.ArticleDetail
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.ui.ArticleView
import me.matsumo.blog.core.ui.AsyncLoadContents
import me.matsumo.blog.feature.articledetail.components.ArticleTitleSection
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun ArticleDetailRoute(
    articleId: Long,
    modifier: Modifier = Modifier,
    viewModel: ArticleDetailViewModel = koinViewModel {
        parametersOf(articleId)
    },
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
        retryAction = viewModel::fetch,
    ) {
        ArticleDetailScreen(
            modifier = Modifier.fillMaxSize(),
            articleDetail = it.article,
        )
    }
}

@Composable
private fun ArticleDetailScreen(
    articleDetail: ArticleDetail,
    modifier: Modifier = Modifier,
) {
    ArticleView(
        modifier = modifier,
        content = articleDetail.body,
        header = {
            ArticleTitleSection(
                modifier = Modifier
                    .widthIn(max = CONTAINER_MAX_WIDTH)
                    .fillMaxWidth()
                    .padding(it)
                    .padding(top = 48.dp)
                    .padding(horizontal = 24.dp),
                title = articleDetail.title,
                publishedAt = articleDetail.publishedAt,
                tags = articleDetail.tags.toImmutableList(),
            )
        },
    )
}
