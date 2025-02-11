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
    val isMobile = LocalDevice.current == Device.MOBILE
    val tableOfContentsWidth = 320.dp
    val contentsContentPadding = PaddingValues(end = if (isMobile) 0.dp else tableOfContentsWidth)

    val markdownSettings = MarkdownSettings.default().copy(
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
                bottom = 2.dp,
            ),
        ),
    )

    val parseTree = MarkdownParser(markdownSettings.flavour).buildMarkdownTreeFromString(articleDetail.body)
    val headers = remember(articleDetail.body) {
        mutableStateListOf(*findHeading(articleDetail.body, parseTree).toTypedArray())
    }

    val state = rememberLazyListState()
    var currentHeaderKey by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.visibleItemsInfo }.collect { itemInfo ->
            for (info in itemInfo) {
                if (info.key.toString().startsWith("header")) {
                    currentHeaderKey = info.key.toString()
                    break
                }
            }
        }
    }

    BoxWithConstraints(modifier) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .widthIn(max = CONTAINER_MAX_WIDTH)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                ArticleTitleSection(
                    modifier = Modifier
                        .widthIn(max = CONTAINER_MAX_WIDTH)
                        .fillMaxWidth()
                        .padding(contentsContentPadding)
                        .padding(top = 48.dp)
                        .padding(horizontal = 24.dp),
                    title = articleDetail.title,
                    publishedAt = articleDetail.publishedAt,
                    tags = articleDetail.tags.toImmutableList(),
                )
            }

            markdownItems(
                content = articleDetail.body,
                parsedTree = parseTree,
                markdownSettings = markdownSettings,
                contentPadding = contentsContentPadding,
            )

            item {
                BlogBottomBar(
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        if (!isMobile) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = (maxWidth - CONTAINER_MAX_WIDTH).coerceAtLeast(0.dp) / 2)
                    .padding(24.dp, 48.dp)
                    .width(tableOfContentsWidth - 40.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                val allowedHeaders =
                    listOf(MarkdownHeader.MarkdownHeaderNode.H1, MarkdownHeader.MarkdownHeaderNode.H2, MarkdownHeader.MarkdownHeaderNode.H3)
                val filteredHeaders = headers.filter { allowedHeaders.contains(it.node) }

                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "Table of Contents",
                    style = MaterialTheme.typography.bodyLarge.bold(),
                    color = MaterialTheme.colorScheme.onSurface,
                )

                for (header in filteredHeaders) {
                    val color: Color
                    val weight: FontWeight

                    if (currentHeaderKey == header.key) {
                        color = MaterialTheme.colorScheme.onSurface
                        weight = FontWeight.ExtraBold
                    } else {
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                        weight = FontWeight.Normal
                    }

                    val indent: Dp = when (header.node) {
                        MarkdownHeader.MarkdownHeaderNode.H1 -> 0.dp
                        MarkdownHeader.MarkdownHeaderNode.H2 -> 16.dp
                        else -> 32.dp
                    }

                    Text(
                        modifier = Modifier.padding(start = indent),
                        text = header.content.replace("#", ""),
                        style = MaterialTheme.typography.bodyMedium,
                        color = color,
                        fontWeight = weight,
                    )
                }
            }
        }
    }
}
