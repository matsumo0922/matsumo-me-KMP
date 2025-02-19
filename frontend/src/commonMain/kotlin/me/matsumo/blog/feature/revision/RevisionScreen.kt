package me.matsumo.blog.feature.revision

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.window_title_revision
import me.matsumo.blog.core.theme.openUrl
import me.matsumo.blog.core.theme.setWindowTitle
import me.matsumo.blog.core.ui.ClickableText
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun RevisionScreen(
    modifier: Modifier = Modifier,
    viewModel: RevisionViewModel = koinViewModel(),
) {
    setWindowTitle(stringResource(Res.string.window_title_revision))

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        SelectionContainer {
            ClickableText(
                onClick = { openUrl("https://github.com/matsumo0922/matsumo-me-KMP/commit/${viewModel.revision}") },
                text = viewModel.revision,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
