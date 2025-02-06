package me.matsumo.blog.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.home_about_me_description
import matsumo_me_kmp.frontend.generated.resources.home_greeting_description
import matsumo_me_kmp.frontend.generated.resources.navigation_about
import me.matsumo.blog.core.ui.CodeTitle
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomeAboutSection(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(24.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                shape = RoundedCornerShape(16.dp),
            )
            .background(MaterialTheme.colorScheme.background.copy(0.5f))
            .padding(24.dp),
    ) {
        AboutMeItem(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AboutMeItem(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CodeTitle(
            modifier = Modifier.fillMaxWidth(),
            text = "AboutMe",
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(Res.string.home_about_me_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}
