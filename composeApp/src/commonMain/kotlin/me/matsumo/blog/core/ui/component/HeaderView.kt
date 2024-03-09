package me.matsumo.blog.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import matsumo_me.composeapp.generated.resources.*
import matsumo_me.composeapp.generated.resources.Res
import matsumo_me.composeapp.generated.resources.navigation_articles
import matsumo_me.composeapp.generated.resources.vec_blog_logo
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.model.WindowWidthSize
import me.matsumo.blog.core.model.isBiggerThan
import me.matsumo.blog.core.model.isDark
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.theme.HEADER_HEIGHT
import me.matsumo.blog.core.theme.LocalThemeConfig
import me.matsumo.blog.core.theme.LocalWindowWidthSize
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun HeaderView(
    onSetThemeConfig: (ThemeConfig) -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowWidthSize = LocalWindowWidthSize.current
    val isDarkTheme = LocalThemeConfig.current.isDark()

    Box(modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(HEADER_HEIGHT)
                .blur(16.dp)
        )

        Row(
            modifier = Modifier
                .widthIn(max = CONTAINER_MAX_WIDTH)
                .align(Alignment.Center)
                .padding(24.dp),
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
                onClick = {
                    onSetThemeConfig.invoke(if (isDarkTheme) ThemeConfig.Light else ThemeConfig.Dark)
                },
            ) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                    contentDescription = "theme",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }

            if (windowWidthSize.isBiggerThan(WindowWidthSize.Compact)) {
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

                if (windowWidthSize.isBiggerThan(WindowWidthSize.Medium)) {
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
    }
}
