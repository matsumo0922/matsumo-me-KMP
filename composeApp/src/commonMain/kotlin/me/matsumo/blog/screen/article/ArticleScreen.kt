package me.matsumo.blog.screen.article

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import me.matsumo.blog.core.model.ArticleDetail
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.ui.AsyncLoadContentsWithHeader
import me.matsumo.blog.screen.home.HomeIdleScreen

@Composable
internal fun ArticleScreen(
    component: ArticleComponent,
    articleId: String,
    modifier: Modifier = Modifier,
) {
    val screenState by component.screenState.collectAsState()

    LaunchedEffect(Unit) {
        if (screenState !is ScreenState.Idle) {
            component.fetch(articleId)
        }
    }

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

}
