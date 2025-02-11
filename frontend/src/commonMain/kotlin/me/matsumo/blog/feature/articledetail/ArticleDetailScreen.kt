package me.matsumo.blog.feature.articledetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mikepenz.markdown.coil3.Coil3ImageTransformerImpl
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.markdownDimens
import com.mikepenz.markdown.model.markdownPadding
import me.matsumo.blog.core.domain.model.ArticleDetail
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.ui.AsyncLoadContents
import me.matsumo.blog.core.ui.BlogBottomBar
import me.matsumo.blog.core.ui.CustomImageTransformer
import me.matsumo.blog.feature.articledetail.components.CustomMarkdownComponents
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun ArticleDetailRoute(
    articleId: Long,
    modifier: Modifier = Modifier,
    viewModel: ArticleDetailViewModel = koinViewModel {
        parametersOf(articleId)
    }
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
        retryAction = viewModel::fetch
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

    val flavor = GFMFlavourDescriptor()
    val parsedTree = MarkdownParser(flavor).buildMarkdownTreeFromString(articleDetail.body)

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Markdown(
                modifier = Modifier.widthIn(max = CONTAINER_MAX_WIDTH),
                content = articleDetail.body,
                imageTransformer = CustomImageTransformer,
                components = CustomMarkdownComponents.build(),
                typography = markdownTypography(
                    textLink = TextLinkStyles(
                        style = TextStyle(color = MaterialTheme.colorScheme.primary).toSpanStyle(),
                        hoveredStyle = TextStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline).toSpanStyle(),
                    ),
                ),
                dimens = markdownDimens(
                    dividerThickness = 1.dp,
                    codeBackgroundCornerSize = 12.dp,
                    blockQuoteThickness = 2.dp,
                    tableMaxWidth = Dp.Unspecified,
                    tableCellWidth = 160.dp,
                    tableCellPadding = 16.dp,
                    tableCornerSize = 8.dp,
                ),
                padding = markdownPadding(
                    block = 8.dp,
                    list = 12.dp,
                    listItemBottom = 8.dp,
                    listIndent = 8.dp,
                    codeBlock = PaddingValues(16.dp),
                    blockQuote = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                    blockQuoteText = PaddingValues(vertical = 4.dp),
                    blockQuoteBar = PaddingValues.Absolute(
                        left = 4.dp,
                        top = 2.dp,
                        right = 4.dp,
                        bottom = 2.dp
                    ),
                )
            )
        }

        item {
            BlogBottomBar(
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
