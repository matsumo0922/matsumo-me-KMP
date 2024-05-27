package me.matsumo.blog.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import matsumo_me.composeapp.generated.resources.Res
import matsumo_me.composeapp.generated.resources.navigation_about
import matsumo_me.composeapp.generated.resources.navigation_articles
import matsumo_me.composeapp.generated.resources.navigation_github
import matsumo_me.composeapp.generated.resources.vec_blog_logo
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.model.isDark
import me.matsumo.blog.core.ui.theme.CONTAINER_MAX_WIDTH
import me.matsumo.blog.core.ui.theme.HEADER_HEIGHT
import me.matsumo.blog.core.ui.theme.LocalThemeConfig
import me.matsumo.blog.core.ui.theme.LocalWindowWidthSize
import me.matsumo.blog.core.ui.theme.isBiggerThan
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
                    imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                    contentDescription = "theme",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }

            if (windowWidthSize.isBiggerThan(WindowWidthSizeClass.Compact)) {
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

                if (windowWidthSize.isBiggerThan(WindowWidthSizeClass.Medium)) {
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
