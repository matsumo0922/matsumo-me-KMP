package me.matsumo.blog.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.navigation_about
import matsumo_me_kmp.frontend.generated.resources.navigation_articles
import matsumo_me_kmp.frontend.generated.resources.navigation_github
import matsumo_me_kmp.frontend.generated.resources.navigation_home
import matsumo_me_kmp.frontend.generated.resources.vec_blog_logo
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun BlogTopAppBar(
    isMobile: Boolean,
    isDark: Boolean,
    onToggleThemeClicked: () -> Unit,
    onOpenDrawerClicked: () -> Unit,
    onNavigationHomeClicked: () -> Unit,
    onNavigationAboutClicked: () -> Unit,
    onNavigationArticlesClicked: () -> Unit,
    onNavigationGithubClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .height(80.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(4.dp)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = { onNavigationHomeClicked() },
                ),
            imageVector = vectorResource(Res.drawable.vec_blog_logo),
            contentDescription = "matsumo.me",
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onToggleThemeClicked) {
            Icon(
                imageVector = if (isDark) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                contentDescription = "Theme",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }

        if (!isMobile) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally,
                )
            ) {
                TextButton(onNavigationHomeClicked) {
                    Text(
                        text = stringResource(Res.string.navigation_home),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                TextButton(onNavigationAboutClicked) {
                    Text(
                        text = stringResource(Res.string.navigation_about),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                TextButton(onNavigationArticlesClicked) {
                    Text(
                        text = stringResource(Res.string.navigation_articles),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                TextButton(onNavigationGithubClicked) {
                    Text(
                        text = stringResource(Res.string.navigation_github),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }

        if (isMobile) {
            IconButton(onOpenDrawerClicked) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}
