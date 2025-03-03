package me.matsumo.blog.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.matsumo.blog.core.theme.openUrl
import me.matsumo.blog.core.theme.rememberWindowWidthDp
import me.matsumo.blog.core.ui.utils.clickableWithPointer
import me.matsumo.blog.core.ui.utils.toCrosProxyUrl
import me.matsumo.blog.shared.model.OgContents

@Composable
fun LinkCard(
    url: String,
    onOgContentsRequested: suspend (String) -> OgContents?,
    modifier: Modifier = Modifier,
) {
    val windowWidthDp by rememberWindowWidthDp()
    var ogContents by remember { mutableStateOf<OgContents?>(null) }

    LaunchedEffect(url) {
        if (ogContents == null) {
            ogContents = onOgContentsRequested(url)
        }
    }

    Card(
        modifier = modifier
            .height(160.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickableWithPointer { openUrl(url) }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                shape = RoundedCornerShape(8.dp),
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmer(ogContents == null),
                    text = ogContents?.title.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmer(ogContents == null),
                    text = ogContents?.description.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmer(ogContents == null),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (ogContents != null) Color.White else Color.Transparent)
                            .size(20.dp)
                            .padding(1.dp),
                        model = ogContents?.iconUrl?.toCrosProxyUrl(),
                        contentDescription = null,
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = ogContents?.siteName.orEmpty(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                    )
                }
            }

            AsyncImage(
                modifier = Modifier
                    .widthIn(
                        min = if (ogContents == null) 160.dp else 0.dp,
                        max = windowWidthDp / 3,
                    )
                    .height(160.dp)
                    .background(if (ogContents != null) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent)
                    .shimmer(ogContents == null, 0.dp),
                model = ogContents?.imageUrl?.toCrosProxyUrl(),
                contentDescription = null,
            )
        }
    }
}
