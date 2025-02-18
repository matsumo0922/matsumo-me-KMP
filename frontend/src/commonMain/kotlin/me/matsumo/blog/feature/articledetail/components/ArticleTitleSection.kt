package me.matsumo.blog.feature.articledetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.article_detail_continue_read
import matsumo_me_kmp.frontend.generated.resources.vec_github_icon
import matsumo_me_kmp.frontend.generated.resources.vec_qiita_icon
import matsumo_me_kmp.frontend.generated.resources.vec_zenn_icon
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.theme.LocalDevice
import me.matsumo.blog.core.theme.bold
import me.matsumo.blog.core.theme.openUrl
import me.matsumo.blog.core.theme.semiBold
import me.matsumo.blog.shared.entity.ArticleSource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ArticleTitleSection(
    title: String,
    publishedAt: String,
    tags: ImmutableList<String>,
    extraSource: ArticleSource?,
    extraSourceUrl: String?,
    modifier: Modifier = Modifier,
) {
    val device = LocalDevice.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = title,
            style = MaterialTheme.typography.displaySmall.bold(),
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = publishedAt,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (tag in tags) {
                Text(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                            shape = CircleShape,
                        )
                        .background(MaterialTheme.colorScheme.background.copy(0.5f))
                        .padding(12.dp, 8.dp),
                    text = tag,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        if (extraSource != null && extraSourceUrl != null) {
            val iconId = "iconId"
            val interactionSource = remember { MutableInteractionSource() }
            val isHovered by interactionSource.collectIsHoveredAsState()

            Text(
                modifier = Modifier
                    .align(if (device == Device.DESKTOP) Alignment.End else Alignment.Start)
                    .padding(top = 4.dp)
                    .hoverable(interactionSource)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { openUrl(extraSourceUrl) },
                    ),
                text = buildAnnotatedString {
                    appendInlineContent(iconId)
                    append(stringResource(Res.string.article_detail_continue_read, extraSource.toSourceString()))
                },
                style = MaterialTheme.typography.bodyMedium.semiBold().copy(
                    textDecoration = if (isHovered) TextDecoration.Underline else TextDecoration.None,
                ),
                color = MaterialTheme.colorScheme.onBackground,
                inlineContent = mapOf(
                    iconId to InlineTextContent(
                        placeholder = Placeholder(22.sp, 16.sp, PlaceholderVerticalAlign.TextCenter),
                        children = {
                            Icon(
                                modifier = Modifier.padding(end = 6.dp),
                                painter = painterResource(extraSource.toIcon()),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        },
                    ),
                ),
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 48.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
        )
    }
}

private fun ArticleSource.toSourceString() = when (this) {
    ArticleSource.MARKDOWN -> "GitHub"
    ArticleSource.QIITA -> "Qiita"
    ArticleSource.ZENN -> "Zenn"
}

private fun ArticleSource.toIcon() = when (this) {
    ArticleSource.MARKDOWN -> Res.drawable.vec_github_icon
    ArticleSource.QIITA -> Res.drawable.vec_qiita_icon
    ArticleSource.ZENN -> Res.drawable.vec_zenn_icon
}
