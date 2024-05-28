package me.matsumo.blog.feature.article

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.compose.components.MarkdownComponentModel
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import me.matsumo.blog.core.common.extensions.drawVerticalScrollbar
import me.matsumo.blog.core.model.ArticleDetail
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.model.WebMetadata
import me.matsumo.blog.core.ui.AsyncLoadContentsWithHeader
import me.matsumo.blog.core.ui.components.FooterView
import me.matsumo.blog.core.ui.components.markdown.CustomMarkdownComponents
import me.matsumo.blog.core.ui.extensions.rememberArticleEdgeSpace
import me.matsumo.blog.core.ui.theme.HEADER_HEIGHT
import me.matsumo.blog.core.ui.theme.LocalWindowWidthSize
import me.matsumo.blog.core.ui.theme.isBiggerThan
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun ArticleRoute(
    articleId: String,
    modifier: Modifier = Modifier,
    viewModel: ArticleViewModel = koinViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        if (screenState !is ScreenState.Idle) {
            viewModel.fetch(articleId)
        }
    }

    AsyncLoadContentsWithHeader(
        modifier = modifier,
        screenState = screenState,
        retryAction = { viewModel.fetch(articleId) },
        onSetThemeConfig = viewModel::setThemeConfig,
    ) {
        ArticleScreen(
            modifier = Modifier.fillMaxSize(),
            articleDetail = it.article,
            onRequestWebMetadata = viewModel::getWebMetadata,
            onSetThemeConfig = viewModel::setThemeConfig,
        )
    }
}

@Composable
private fun ArticleScreen(
    articleDetail: ArticleDetail,
    onRequestWebMetadata: suspend (String) -> WebMetadata,
    onSetThemeConfig: (ThemeConfig) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyListState()
    val windowWidthSizeClass = LocalWindowWidthSize.current

    val space by rememberArticleEdgeSpace()
    val isShowTableOfContents by remember(windowWidthSizeClass) {
        mutableStateOf(windowWidthSizeClass.isBiggerThan(WindowWidthSizeClass.Compact))
    }

    val flavor = GFMFlavourDescriptor()
    val parsedTree = MarkdownParser(flavor).buildMarkdownTreeFromString(articleDetail.body)

    for (node in parsedTree.children) {
        val model = MarkdownComponentModel(
            content = articleDetail.body,
            node = node,
            typography = markdownTypography()
        )

        Logger.d { node.type.name }
        Logger.d { model.node.getTextInNode(articleDetail.body).toString() }
    }



    LazyColumn(
        modifier = modifier.drawVerticalScrollbar(state),
        state = state,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HEADER_HEIGHT + 16.dp)
            )
        }

        item {
            Markdown(
                modifier = Modifier
                    .padding(horizontal = space)
                    .fillMaxWidth(),
                content = articleDetail.body,
                colors = markdownColor(),
                typography = markdownTypography(),
                components = CustomMarkdownComponents.build(
                    onRequestWebMetadata = onRequestWebMetadata,
                ),
            )
        }

        item {
            FooterView(
                modifier = Modifier
                    .padding(top = 80.dp)
                    .fillMaxWidth(),
                onSetThemeConfig = onSetThemeConfig,
            )
        }
    }
}