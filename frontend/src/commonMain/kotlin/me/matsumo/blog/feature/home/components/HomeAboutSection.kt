package me.matsumo.blog.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.theme.LocalDevice

internal fun LazyListScope.homeAboutSection() {
    item {
        HomeAboutSection(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun HomeAboutSection(
    modifier: Modifier = Modifier,
) {
    val padding = if (LocalDevice.current == Device.MOBILE) 24.dp else 40.dp

    Column(
        modifier = modifier
            .padding(padding)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                shape = RoundedCornerShape(16.dp),
            )
            .background(MaterialTheme.colorScheme.surfaceContainer.copy(0.7f))
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(padding),
    ) {
        HomeAboutMeItem(
            modifier = Modifier.fillMaxWidth(),
        )

        HomeAboutProjectsItem(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
