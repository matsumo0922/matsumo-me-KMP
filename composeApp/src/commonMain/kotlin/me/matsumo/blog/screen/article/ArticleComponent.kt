package me.matsumo.blog.screen.article

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import matsumo_me.composeapp.generated.resources.Res
import matsumo_me.composeapp.generated.resources.common_error
import matsumo_me.composeapp.generated.resources.error_executed
import matsumo_me.composeapp.generated.resources.error_no_data
import me.matsumo.blog.core.model.Article
import me.matsumo.blog.core.model.ArticleDetail
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.repository.MMRepository
import me.matsumo.blog.core.utils.suspendRunCatching
import me.matsumo.blog.screen.home.HomeUiState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface ArticleComponent {
    val screenState: StateFlow<ScreenState<ArticleUiState>>

    fun fetch(id: String)
    fun setThemeConfig(themeConfig: ThemeConfig)
}

class DefaultArticleComponent(
    componentContext: ComponentContext,
    private val articleId: String,
    private val setThemeConfig: (ThemeConfig) -> Unit,
) : ArticleComponent, KoinComponent, ComponentContext by componentContext {

    private val mmRepository by inject<MMRepository>()
    private val ioDispatcher by inject<CoroutineDispatcher>()

    private val scope = coroutineScope(ioDispatcher + SupervisorJob())
    private var _screenState = MutableStateFlow<ScreenState<ArticleUiState>>(ScreenState.Loading)

    override val screenState: StateFlow<ScreenState<ArticleUiState>> = _screenState.asStateFlow()

    init {
        fetch(articleId)
    }

    override fun fetch(id: String) {
        scope.launch {
            _screenState.value = ScreenState.Loading
            _screenState.value = suspendRunCatching {
                ArticleUiState(
                    article = mmRepository.getArticle(id)
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
}

@Stable
data class ArticleUiState(
    val article: ArticleDetail,
)
