package me.matsumo.blog.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.bottom_bar_copy_right
import matsumo_me_kmp.frontend.generated.resources.bottom_bar_google_analytics
import matsumo_me_kmp.frontend.generated.resources.bottom_bar_powered_by
import matsumo_me_kmp.frontend.generated.resources.bottom_bar_store
import matsumo_me_kmp.frontend.generated.resources.im_get_on_apple_store
import matsumo_me_kmp.frontend.generated.resources.im_get_on_google_play
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.theme.LocalDevice
import me.matsumo.blog.core.theme.openUrl
import me.matsumo.blog.core.theme.semiBold
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BlogBottomBar(
    modifier: Modifier = Modifier,
) {
    val isMobile = LocalDevice.current == Device.MOBILE
    val padding = if (LocalDevice.current == Device.MOBILE) 24.dp else 40.dp

    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surface),
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )

        NonLazyVerticalGrid(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            columns = if (isMobile) 1 else 2,
            horizontalSpacing = 24.dp,
            verticalSpacing = 48.dp,
        ) {
            InfoSection(
                modifier = Modifier.then(
                    if (isMobile) Modifier.wrapContentHeight() else Modifier.fillMaxHeight(),
                ),
                isMobile = isMobile,
            )

            LinkSection(
                modifier = Modifier.then(
                    if (isMobile) Modifier.wrapContentHeight() else Modifier.fillMaxHeight(),
                ),
                isMobile = isMobile,
            )
        }
    }
}

@Composable
private fun InfoSection(
    isMobile: Boolean,
    modifier: Modifier = Modifier,
) {
    val year by rememberSaveable {
        mutableIntStateOf(
            Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .year,
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.Bottom,
        ),
        horizontalAlignment = if (isMobile) Alignment.CenterHorizontally else Alignment.Start,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.clickable { openUrl("https://twitter.com/matsumo0922") },
                text = "Twitter",
                style = MaterialTheme.typography.bodyMedium.semiBold(),
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                modifier = Modifier.clickable { openUrl("https://zenn.dev/matsumo0922") },
                text = "GitHub",
                style = MaterialTheme.typography.bodyMedium.semiBold(),
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                modifier = Modifier.clickable { openUrl("https://zenn.dev/matsumo0922") },
                text = "Zenn",
                style = MaterialTheme.typography.bodyMedium.semiBold(),
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(Res.string.bottom_bar_google_analytics),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            text = stringResource(Res.string.bottom_bar_copy_right, year),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun LinkSection(
    isMobile: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (isMobile) Alignment.CenterHorizontally else Alignment.End,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Bottom,
        ),
    ) {
        Text(
            text = stringResource(Res.string.bottom_bar_powered_by),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = if (isMobile) TextAlign.Center else TextAlign.End,
        )

        Text(
            text = stringResource(Res.string.bottom_bar_store),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = if (isMobile) TextAlign.Center else TextAlign.End,
        )

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f, false),
                painter = painterResource(Res.drawable.im_get_on_apple_store),
                contentDescription = "App Store",
            )

            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f, false),
                painter = painterResource(Res.drawable.im_get_on_google_play),
                contentDescription = "Google Play",
            )
        }
    }
}
