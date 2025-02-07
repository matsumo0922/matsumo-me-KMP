package me.matsumo.blog.feature.home

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.domain.ThemeConfig
import me.matsumo.blog.core.theme.LocalDevice
import me.matsumo.blog.core.theme.LocalThemeConfig
import me.matsumo.blog.feature.home.components.HomeAboutSection
import me.matsumo.blog.feature.home.components.HomeContactSection
import me.matsumo.blog.feature.home.components.HomeHelloSection
import me.matsumo.blog.feature.home.components.homeExperienceSection
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
) {
    val isMobile = LocalDevice.current == Device.MOBILE

    BoxWithConstraints(modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                HomeHelloSection(
                    modifier = Modifier
                        .height(maxHeight)
                        .fillMaxWidth(),
                )
            }

            item {
                HomeAboutSection(
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            homeExperienceSection(
                horizontalPadding = (if (isMobile) 24.dp else 40.dp) * 2
            )

            item {
                HomeContactSection(
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
