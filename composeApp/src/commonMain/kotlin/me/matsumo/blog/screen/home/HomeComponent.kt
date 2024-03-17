package me.matsumo.blog.screen.home

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import matsumo_me.composeapp.generated.resources.*
import matsumo_me.composeapp.generated.resources.Res
import me.matsumo.blog.core.model.Article
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.repository.MMRepository
import me.matsumo.blog.core.utils.suspendRunCatching
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface HomeComponent {
    val screenState: StateFlow<ScreenState<HomeUiState>>

    fun fetch()
    fun setThemeConfig(themeConfig: ThemeConfig)

    fun onNavigateToAbout()
    fun onNavigateToArticle(articleId: String)
}

class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val setThemeConfig: (ThemeConfig) -> Unit,
    private val navigateToAbout: () -> Unit,
    private val navigateToArticle: (String) -> Unit,
) : HomeComponent, KoinComponent, ComponentContext by componentContext {

    private val mmRepository by inject<MMRepository>()
    private val ioDispatcher by inject<CoroutineDispatcher>()

    private val scope = coroutineScope(ioDispatcher + SupervisorJob())
    private val _screenState = MutableStateFlow<ScreenState<HomeUiState>>(ScreenState.Loading)

    override val screenState: StateFlow<ScreenState<HomeUiState>> = _screenState.asStateFlow()

    override fun fetch() {
        scope.launch {
            _screenState.value = ScreenState.Loading
            _screenState.value = suspendRunCatching {
                HomeUiState(
                    articles = mmRepository.getArticles()
                )
            }.fold(
                onSuccess = { ScreenState.Idle(it) },
                onFailure = { ScreenState.Error(Res.string.error_no_data) },
            )
        }
    }

    override fun setThemeConfig(themeConfig: ThemeConfig) {
        setThemeConfig.invoke(themeConfig)
    }

    override fun onNavigateToAbout() {
        navigateToAbout.invoke()
    }

    override fun onNavigateToArticle(articleId: String) {
        navigateToArticle.invoke(articleId)
    }
}

@Stable
data class HomeUiState(
    val articles: List<Article>,
)
