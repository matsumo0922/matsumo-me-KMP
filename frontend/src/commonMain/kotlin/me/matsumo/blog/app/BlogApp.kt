package me.matsumo.blog.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.navigation_about
import matsumo_me_kmp.frontend.generated.resources.navigation_articles
import matsumo_me_kmp.frontend.generated.resources.navigation_github
import matsumo_me_kmp.frontend.generated.resources.navigation_home
import matsumo_me_kmp.frontend.generated.resources.vec_blog_logo
import me.matsumo.blog.app.components.BlogDrawerContent
import me.matsumo.blog.app.components.BlogNavHost
import me.matsumo.blog.app.components.BlogTopAppBar
import me.matsumo.blog.core.domain.Destinations
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.domain.isDark
import me.matsumo.blog.core.theme.BlogTheme
import me.matsumo.blog.core.theme.rememberDeviceState
import me.matsumo.blog.core.ui.ModalNavigationDrawerWrapper
import me.matsumo.blog.core.ui.utils.toUrlPath
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BlogApp(
    modifier: Modifier = Modifier,
    viewModel: BlogViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val device by rememberDeviceState()

    BlogTheme(
        themeConfig = uiState.theme,
        device = device,
    ) {
        BlogScreen(
            modifier = modifier.fillMaxSize(),
            isMobile = device == Device.MOBILE,
            isDark = uiState.theme.isDark(),
            onToggleThemeClicked = viewModel::toggleTheme,
        )
    }
}

@Composable
private fun BlogScreen(
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
                onNavigationHomeClicked = { },
                onNavigationAboutClicked = { },
                onNavigationArticlesClicked = { },
                onNavigationGithubClicked = { },
            )
        },
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                BlogTopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    isMobile = isMobile,
                    isDark = isDark,
                    onToggleThemeClicked = onToggleThemeClicked,
                    onOpenDrawerClicked = { scope.launch { drawerState.open() } },
                    onNavigationAboutClicked = { },
                    onNavigationArticlesClicked = { navController.navigate(Destinations.Articles("10", "Hello").also {
                        Napier.d { "Navigate to ${it.toUrlPath()}" }
                    }) },
                    onNavigationGithubClicked = { },
                    onNavigationHomeClicked = { },
                )
            }
        ) {
            BlogNavHost(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                navController = navController,
            )
        }
    }
}
