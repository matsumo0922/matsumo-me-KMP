package me.matsumo.blog.feature.articledetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.markdownDimens
import com.mikepenz.markdown.model.markdownPadding
import kotlinx.collections.immutable.toImmutableList
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.domain.model.ArticleDetail
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.theme.LocalDevice
import me.matsumo.blog.core.theme.bold
import me.matsumo.blog.core.ui.ArticleView
import me.matsumo.blog.core.ui.AsyncLoadContents
import me.matsumo.blog.core.ui.BlogBottomBar
import me.matsumo.blog.core.ui.CustomImageTransformer
import me.matsumo.blog.feature.articledetail.components.ArticleTitleSection
import me.matsumo.blog.feature.articledetail.components.CustomMarkdownComponents
import me.matsumo.blog.feature.articledetail.components.MarkdownHeader
import me.matsumo.blog.feature.articledetail.components.MarkdownSettings
import me.matsumo.blog.feature.articledetail.components.findHeading
import me.matsumo.blog.feature.articledetail.components.markdownItems
import org.intellij.markdown.parser.MarkdownParser
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
       }
   )
}
