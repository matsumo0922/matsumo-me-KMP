package me.matsumo.blog.feature.application.team

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.app_team_of_service
import matsumo_me_kmp.frontend.generated.resources.app_team_of_service_description
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.ui.ArticleView
import me.matsumo.blog.core.ui.AsyncLoadContents
import me.matsumo.blog.feature.application.ApplicationArticleHeader
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun TeamOfServiceRoute(
    appName: String,
    modifier: Modifier = Modifier,
    viewModel: TeamOfServiceViewModel = koinViewModel {
        parametersOf(appName)
    },
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
        retryAction = viewModel::fetch,
    ) {
        TeamOfServiceScreen(
            modifier = Modifier.fillMaxSize(),
            content = it.content,
        )
    }
}

@Composable
private fun TeamOfServiceScreen(
    content: String,
    modifier: Modifier = Modifier,
) {
    ArticleView(
        modifier = modifier,
        content = content,
        header = {
            ApplicationArticleHeader(
                modifier = Modifier
                    .widthIn(max = CONTAINER_MAX_WIDTH)
                    .fillMaxWidth()
                    .padding(it)
                    .padding(top = 48.dp)
                    .padding(horizontal = 24.dp),
                title = stringResource(Res.string.app_team_of_service),
                description = stringResource(Res.string.app_team_of_service_description),
            )
        },
    )
}
