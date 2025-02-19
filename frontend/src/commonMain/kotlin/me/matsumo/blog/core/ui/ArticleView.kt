package me.matsumo.blog.core.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.sp
import com.mikepenz.markdown.compose.extendedspans.ExtendedSpans
import com.mikepenz.markdown.compose.extendedspans.RoundedCornerSpanPainter
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.markdownAnimations
import com.mikepenz.markdown.model.markdownDimens
import com.mikepenz.markdown.model.markdownExtendedSpans
import com.mikepenz.markdown.model.markdownPadding
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.theme.bold
import me.matsumo.blog.core.theme.rememberWindowWidthDp
import me.matsumo.blog.feature.articledetail.components.CustomMarkdownComponents
import me.matsumo.blog.feature.articledetail.components.MarkdownHeader
import me.matsumo.blog.feature.articledetail.components.MarkdownSettings
import me.matsumo.blog.feature.articledetail.components.findHeading
import me.matsumo.blog.feature.articledetail.components.markdownItems
import me.matsumo.blog.shared.model.OgContents
import org.intellij.markdown.parser.MarkdownParser
import kotlin.math.abs

@Composable
fun ArticleView(
    content: String,
    modifier: Modifier = Modifier,
    onOgContentsRequested: suspend (String) -> OgContents = { error("Not supported on this component.") },
    header: @Composable (PaddingValues) -> Unit,
) {
    val windowWidthDp by rememberWindowWidthDp()
    val isMobile = Device.fromWidth(windowWidthDp - 120.dp) == Device.MOBILE
    val tableOfContentsWidth = 320.dp
    val contentsContentPadding = PaddingValues(end = if (isMobile) 0.dp else tableOfContentsWidth)

    val markdownSettings = MarkdownSettings.default().copy(
        imageTransformer = CustomImageTransformer,
        components = CustomMarkdownComponents.build(
            onOgContentsRequested = onOgContentsRequested,
        ),
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
        animations = markdownAnimations(
            animateTextSize = { this },
        ),
        extendedSpans = markdownExtendedSpans {
            remember {
                ExtendedSpans(
                    RoundedCornerSpanPainter(
                        cornerRadius = 4.sp,
                        padding = RoundedCornerSpanPainter.TextPaddingValues(1.sp, 2.sp),
                        topMargin = 2.sp,
                        bottomMargin = 0.sp
                    )
                )
            }
        }
    )

    val parseTree = MarkdownParser(markdownSettings.flavour).buildMarkdownTreeFromString(content)
    val headers = remember(content) {
        mutableStateListOf(*findHeading(content, parseTree).toTypedArray())
    }

    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    var currentVisibleIndex by remember { mutableStateOf(0) }

    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.visibleItemsInfo }.collect { itemInfo ->
            currentVisibleIndex = itemInfo.minOf { it.index }.coerceAtLeast(0)
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

        SelectionContainer {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = state,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    header(contentsContentPadding)
                }

                markdownItems(
                    content = content,
                    parsedTree = parseTree,
                    markdownSettings = markdownSettings,
                    contentPadding = contentsContentPadding,
                )

                item {
                    Spacer(
                        modifier = Modifier.height(48.dp),
                    )
                }

                item {
                    BlogBottomBar(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }

        if (!isMobile) {
            SelectionContainer(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = (maxWidth - CONTAINER_MAX_WIDTH).coerceAtLeast(0.dp) / 2)
                    .padding(24.dp, 48.dp)
                    .width(tableOfContentsWidth - 40.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    val allowedHeaders =
                        listOf(MarkdownHeader.MarkdownHeaderNode.H1, MarkdownHeader.MarkdownHeaderNode.H2, MarkdownHeader.MarkdownHeaderNode.H3)
                    val filteredHeaders = headers.filter { allowedHeaders.contains(it.node) }
                    val currentHeader = filteredHeaders
                        .filter { it.index <= currentVisibleIndex + 1 }
                        .minBy { abs(currentVisibleIndex - it.index) }

                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "Table of Contents",
                        style = MaterialTheme.typography.bodyLarge.bold(),
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    for (filteredHeader in filteredHeaders) {
                        val color: Color
                        val weight: FontWeight

                        if (currentHeader == filteredHeader) {
                            color = MaterialTheme.colorScheme.onSurface
                            weight = FontWeight.ExtraBold
                        } else {
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                            weight = FontWeight.Normal
                        }

                        val indent: Dp = when (filteredHeader.node) {
                            MarkdownHeader.MarkdownHeaderNode.H1 -> 0.dp
                            MarkdownHeader.MarkdownHeaderNode.H2 -> 16.dp
                            else -> 32.dp
                        }

                        val animateColor by animateColorAsState(color)
                        val animateWeight by animateIntAsState(weight.weight)

                        ClickableText(
                            modifier = Modifier.padding(start = indent),
                            onClick = {
                                scope.launch {
                                    runCatching { state.animateScrollToItem(filteredHeader.index) }.onFailure {
                                        Napier.e("Failed to scroll to item: ${filteredHeader.index}", it)
                                    }
                                }
                            },
                            text = filteredHeader.content.replace("#", ""),
                            style = MaterialTheme.typography.bodyMedium,
                            color = animateColor,
                            fontWeight = FontWeight(animateWeight),
                            lineHeight = 22.sp,
                        )
                    }
                }
            }
        }
    }
}
