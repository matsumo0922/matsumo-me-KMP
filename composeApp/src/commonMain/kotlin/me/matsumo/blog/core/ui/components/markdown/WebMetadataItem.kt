package me.matsumo.blog.core.ui.components.markdown

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.ktor.http.URLBuilder
import io.ktor.http.set
import me.matsumo.blog.core.model.WebMetadata
import me.matsumo.blog.core.ui.theme.bold
import me.matsumo.blog.core.ui.theme.primary

@Composable
fun WebMetadataItem(
    url: String,
    onRequestWebMetadata: suspend (String) -> WebMetadata,
    modifier: Modifier = Modifier,
) {
    var metadata: WebMetadata = rememberSaveable(url) { WebMetadata.Unknown(url) }

    if (metadata is WebMetadata.Unknown) {
        LaunchedEffect(url) {
            metadata = onRequestWebMetadata(url)
        }
    }

    when (metadata) {
        is WebMetadata.IFrame -> {

        }
        is WebMetadata.OG -> {
            OgItem(
                modifier = modifier,
                metadata = metadata as WebMetadata.OG,
            )
        }
        is WebMetadata.Unknown -> {
            Text(
                modifier = modifier,
                text = url,
                style = MaterialTheme.typography.bodyLarge.primary().bold(),
            )
        }
    }
}

@Composable
private fun OgItem(
    metadata: WebMetadata.OG,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = metadata.title,
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    text = metadata.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        modifier = Modifier.size(24.dp),
                        model = getFaviconUrl(metadata.url),
                        contentDescription = null,
                    )

                    Text(
                        text = metadata.siteName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        AsyncImage(
            modifier = Modifier.aspectRatio(1f),
            model = metadata.image,
            contentDescription = null,
        )
    }
}

private fun getFaviconUrl(url: String): String {
    return URLBuilder(url).apply {
        set(path = "/favicon.ico")
    }.buildString()
}