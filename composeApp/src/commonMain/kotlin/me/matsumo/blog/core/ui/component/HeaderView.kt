package me.matsumo.blog.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import matsumo_me.composeapp.generated.resources.*
import matsumo_me.composeapp.generated.resources.Res
import matsumo_me.composeapp.generated.resources.navigation_articles
import matsumo_me.composeapp.generated.resources.vec_blog_logo
import matsumo_me.composeapp.generated.resources.vec_theme
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .blur(8.dp),
        )

        Row(
            modifier = Modifier
                .widthIn(max = CONTAINER_MAX_WIDTH)
                .align(Alignment.Center)
                .padding(vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Image(
                modifier = Modifier.height(48.dp),
                imageVector = vectorResource(Res.drawable.vec_blog_logo),
                contentDescription = "matsumo.me",
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                modifier = Modifier.size(40.dp),
                onClick = {},
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.vec_theme),
                    contentDescription = "theme",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }

            TextButton(
                onClick = {},
            ) {
                Text(
                    text = stringResource(Res.string.navigation_articles),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            TextButton(
                onClick = {},
            ) {
                Text(
                    text = stringResource(Res.string.navigation_about),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            TextButton(
                onClick = {},
            ) {
                Text(
                    text = stringResource(Res.string.navigation_github),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
