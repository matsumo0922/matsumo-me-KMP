package me.matsumo.blog.feature.application.privacy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.app_privacy_policy
import matsumo_me_kmp.frontend.generated.resources.app_privacy_policy_description
import matsumo_me_kmp.frontend.generated.resources.window_title_privacy_policy
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.theme.setWindowTitle
import me.matsumo.blog.core.ui.ArticleView
import me.matsumo.blog.core.ui.AsyncLoadContents
import me.matsumo.blog.feature.application.ApplicationArticleHeader
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun PrivacyPolicyRoute(
    appName: String,
    modifier: Modifier = Modifier,
    viewModel: PrivacyPolicyViewModel = koinViewModel {
        parametersOf(appName)
    },
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    setWindowTitle(stringResource(Res.string.window_title_privacy_policy, appName))

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
        retryAction = viewModel::fetch,
    ) {
        PrivacyPolicyScreen(
            modifier = Modifier.fillMaxSize(),
            content = it.content,
        )
    }
}

@Composable
private fun PrivacyPolicyScreen(
    content: String,
    modifier: Modifier = Modifier,
) {
    ArticleView(
        modifier = modifier,
        content = content,
        header = {
            SelectionContainer {
                ApplicationArticleHeader(
                    modifier = Modifier
                        .widthIn(max = CONTAINER_MAX_WIDTH)
                        .fillMaxWidth()
                        .padding(it)
                        .padding(top = 48.dp)
                        .padding(horizontal = 24.dp),
                    title = stringResource(Res.string.app_privacy_policy),
                    description = stringResource(Res.string.app_privacy_policy_description),
                )
            }
        },
    )
}
