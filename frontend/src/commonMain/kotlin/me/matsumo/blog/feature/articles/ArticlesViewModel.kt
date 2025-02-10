package me.matsumo.blog.feature.articles

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.error_no_data
import me.matsumo.blog.core.domain.ScreenState
import me.matsumo.blog.core.domain.model.Article
import me.matsumo.blog.core.repository.ArticleRepository
import me.matsumo.blog.core.ui.utils.suspendRunCatching

class ArticlesViewModel(
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState<ArticlesUiState>>(ScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            _screenState.value = suspendRunCatching {
                ArticlesUiState(
                    articles = articleRepository.getArticles()
                )
            }.fold(
                onSuccess = { ScreenState.Idle(it) },
                onFailure = { ScreenState.Error(Res.string.error_no_data) }
            )
        }
    }
}

@Stable
data class ArticlesUiState(
    val articles: List<Article>
)
