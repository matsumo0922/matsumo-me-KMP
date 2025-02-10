package me.matsumo.blog.feature.articles.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import me.matsumo.blog.core.domain.model.Article

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArticleCard(
    article: Article,
    onClickArticle: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClickArticle.invoke(article.id) }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                shape = RoundedCornerShape(8.dp),
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
    ) {
        OGPImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 1),
            title = article.title,
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = article.publishedAt,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                for (tag in article.tags.take(2)) {
                    Text(
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                                shape = CircleShape,
                            )
                            .background(MaterialTheme.colorScheme.background.copy(0.5f))
                            .padding(10.dp, 6.dp),
                        text = tag,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun OGPImage(
    title: String,
    modifier: Modifier = Modifier,
) {
    val containerColor = Color.Transparent
    val primaryColor = lerp(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.tertiaryContainer,
        0.5f
    ).copy(alpha = 0.3f)

    Box(
        modifier = modifier.background(
            Brush.linearGradient(
                colors = listOf(primaryColor, containerColor, containerColor, primaryColor),
                start = Offset(Float.POSITIVE_INFINITY, 0f),
                end = Offset(0f, Float.POSITIVE_INFINITY),
                tileMode = TileMode.Mirror,
            )
        ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
