package me.matsumo.blog.screen.article

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.matsumo.blog.core.model.*
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.theme.HEADER_HEIGHT
import me.matsumo.blog.core.theme.LocalWindowWidthSize
import me.matsumo.blog.core.ui.AsyncLoadContentsWithHeader
import me.matsumo.blog.core.ui.component.ArticleCard
import me.matsumo.blog.core.ui.component.FooterView
import me.matsumo.blog.core.ui.component.NonLazyGrid
import me.matsumo.blog.screen.home.HomeIdleScreen

@Composable
internal fun ArticleScreen(
    component: ArticleComponent,
    modifier: Modifier = Modifier,
) {
    val screenState by component.screenState.collectAsState()

    AsyncLoadContentsWithHeader(
        modifier = modifier.fillMaxSize(),
        screenState = screenState,
        onSetThemeConfig = component::setThemeConfig,
    ) {
        ArticleIdleScreen(
            modifier = Modifier.fillMaxSize(),
            article = it.article,
            onSetThemeConfig = component::setThemeConfig,
            onClickTag = {},
        )
    }
}

@Composable
private fun ArticleIdleScreen(
    article: ArticleDetail,
    onSetThemeConfig: (ThemeConfig) -> Unit,
    onClickTag: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowWidthSize = LocalWindowWidthSize.current
    val isShowTableOfContents by remember(windowWidthSize) {
        mutableStateOf(windowWidthSize.isBiggerThan(WindowWidthSize.Compact))
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

        Text(
            modifier = Modifier
                .widthIn(max = CONTAINER_MAX_WIDTH)
                .padding(horizontal = 24.dp),
            text = article.body,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

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
