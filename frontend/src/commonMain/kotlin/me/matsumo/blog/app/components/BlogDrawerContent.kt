package me.matsumo.blog.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.navigation_about
import matsumo_me_kmp.frontend.generated.resources.navigation_articles
import matsumo_me_kmp.frontend.generated.resources.navigation_github
import matsumo_me_kmp.frontend.generated.resources.navigation_home
import me.matsumo.blog.core.theme.bold
import org.jetbrains.compose.resources.stringResource

@Composable
fun BlogDrawerContent(
    onNavigationHomeClicked: () -> Unit,
    onNavigationAboutClicked: () -> Unit,
    onNavigationArticlesClicked: () -> Unit,
    onNavigationGithubClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet(
        modifier = modifier,
        drawerShape = RoundedCornerShape(
            topStart = 16.dp,
            bottomStart = 16.dp,
        ),
        drawerContainerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 256.dp)
                .padding(vertical = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DrawerItem(
                label = stringResource(Res.string.navigation_home),
                onClick = onNavigationHomeClicked,
            )

            DrawerItem(
                label = stringResource(Res.string.navigation_about),
                onClick = onNavigationAboutClicked,
            )

            DrawerItem(
                label = stringResource(Res.string.navigation_articles),
                onClick = onNavigationArticlesClicked,
            )

            DrawerItem(
                label = stringResource(Res.string.navigation_github),
                onClick = onNavigationGithubClicked,
            )
        }
    }
}

@Composable
private fun DrawerItem(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(start = 16.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 32.dp,
                    bottomStart = 32.dp,
                ),
            )
            .clickable { onClick() }
            .padding(24.dp, 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = label,
            style = MaterialTheme.typography.titleMedium.bold(),
        )
    }
}
