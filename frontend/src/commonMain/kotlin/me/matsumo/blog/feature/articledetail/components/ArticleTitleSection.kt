package me.matsumo.blog.feature.articledetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import me.matsumo.blog.core.theme.bold

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ArticleTitleSection(
    title: String,
    publishedAt: String,
    tags: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
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

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 48.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
        )
    }
}
