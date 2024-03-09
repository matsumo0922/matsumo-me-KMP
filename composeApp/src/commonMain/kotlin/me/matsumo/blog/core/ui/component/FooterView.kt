package me.matsumo.blog.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.datetime.*
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.model.WindowWidthSize
import me.matsumo.blog.core.model.isBiggerThan
import me.matsumo.blog.core.theme.LocalThemeConfig
import me.matsumo.blog.core.theme.LocalWindowWidthSize
import me.matsumo.blog.core.theme.end
import me.matsumo.blog.core.utils.openUrl

@Composable
fun FooterView(
    onSetThemeConfig: (ThemeConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    val themeConfig = LocalThemeConfig.current
    val year = remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year }

    Column(
        modifier = modifier.padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                LinkSection(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth(),
                    text = "This site uses Google Analytics.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth(),
                    text = "©$year daichi-matsumoto",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            ThemeConfigSelector(
                modifier = Modifier.padding(end = 40.dp),
                currentThemeConfig = themeConfig,
                onSetThemeConfig = onSetThemeConfig,
            )
        }
    }
}

@Composable
private fun LinkSection(
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    val windowWidthSize = LocalWindowWidthSize.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextButton(
            onClick = { openUrl("https://github.com/matsumo0922") },
        ) {
            Text(
                text = "GitHub",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        TextButton(
            onClick = { openUrl("https://twitter.com/matsumo0922") },
        ) {
            Text(
                text = "Twitter",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        if (windowWidthSize.isBiggerThan(WindowWidthSize.Compact)) {
            TextButton(
                onClick = { openUrl("https://qiita.com/matsumo0922") },
            ) {
                Text(
                    text = "Qiita",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            TextButton(
                onClick = { openUrl("https://zenn.dev/matsumo0922") },
            ) {
                Text(
                    text = "Zenn",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
private fun ThemeConfigSelector(
    currentThemeConfig: ThemeConfig,
    onSetThemeConfig: (ThemeConfig) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedModifier = Modifier
        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
        .border(
            width = 1.5.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = CircleShape,
        )

    Row(
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                shape = CircleShape,
            )
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        for (theme in listOf(ThemeConfig.Light, ThemeConfig.System, ThemeConfig.Dark)) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .then(if (theme == currentThemeConfig) selectedModifier else Modifier)
                    .clickable { onSetThemeConfig.invoke(theme) }
                    .padding(10.dp),
                imageVector = when (theme) {
                    ThemeConfig.Light -> Icons.Default.LightMode
                    ThemeConfig.System -> Icons.Default.Devices
                    ThemeConfig.Dark -> Icons.Default.DarkMode
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
