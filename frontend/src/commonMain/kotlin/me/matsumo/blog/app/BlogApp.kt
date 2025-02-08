package me.matsumo.blog.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import me.matsumo.blog.app.components.BlogBackground
import me.matsumo.blog.app.components.BlogDrawerContent
import me.matsumo.blog.app.components.BlogNavHost
import me.matsumo.blog.app.components.BlogTopAppBar
import me.matsumo.blog.core.domain.Destinations
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.domain.StaticUrl
import me.matsumo.blog.core.domain.isDark
import me.matsumo.blog.core.theme.BlogTheme
import me.matsumo.blog.core.theme.getNotoSansJPFontFamily
import me.matsumo.blog.core.theme.openUrl
import me.matsumo.blog.core.theme.rememberDeviceState
import me.matsumo.blog.core.ui.ModalNavigationDrawerWrapper
import me.matsumo.blog.core.ui.utils.navigateInclusive
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BlogApp(
    modifier: Modifier = Modifier,
    viewModel: BlogViewModel = koinViewModel(),
    fontFamily: FontFamily? = null,
    startDestinations: Destinations = Destinations.Home,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val device by rememberDeviceState()

    BlogTheme(
        themeConfig = uiState.theme,
        fonts = fontFamily,
        device = device,
    ) {
        BlogBackground(
            modifier = modifier.fillMaxSize(),
            pointCount = if (device == Device.MOBILE) 100 else 200,
            pointColor1 = MaterialTheme.colorScheme.primaryContainer,
            pointColor2 = MaterialTheme.colorScheme.tertiaryContainer,
        ) {
            BlogScreen(
                modifier = Modifier.fillMaxSize(),
                startDestinations = startDestinations,
                isMobile = device == Device.MOBILE,
                isDark = uiState.theme.isDark(),
                onToggleThemeClicked = viewModel::toggleTheme,
            )
        }
    }
}

@Composable
private fun BlogScreen(
    startDestinations: Destinations,
    isMobile: Boolean,
    isDark: Boolean,
    onToggleThemeClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawerWrapper(
        modifier = modifier,
        gesturesEnabled = isMobile,
        drawerState = drawerState,
        drawerContent = {
            BlogDrawerContent(
                state = drawerState,
                onNavigationHomeClicked = { navController.navigateInclusive(Destinations.Home) },
                onNavigationAboutClicked = { },
                onNavigationArticlesClicked = { navController.navigateInclusive(Destinations.Articles) },
                onNavigationGithubClicked = { openUrl(StaticUrl.GITHUB) },
            )
        },
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Column {
                    BlogTopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        isMobile = isMobile,
                        isDark = isDark,
                        onToggleThemeClicked = onToggleThemeClicked,
                        onOpenDrawerClicked = { scope.launch { drawerState.open() } },
                        onNavigationHomeClicked = { navController.navigateInclusive(Destinations.Home) },
                        onNavigationAboutClicked = { },
                        onNavigationArticlesClicked = { navController.navigateInclusive(Destinations.Articles) },
                        onNavigationGithubClicked = { openUrl(StaticUrl.GITHUB) },
                    )

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    )
                }
            },
            containerColor = Color.Transparent,
        ) {
            BlogNavHost(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                navController = navController,
                startDestination = startDestinations,
            )
        }
    }
}
