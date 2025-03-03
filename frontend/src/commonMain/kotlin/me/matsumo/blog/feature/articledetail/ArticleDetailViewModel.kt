package me.matsumo.blog.feature.articledetail

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.error_no_data
import me.matsumo.blog.core.domain.ScreenState
import me.matsumo.blog.core.repository.ArticleRepository
import me.matsumo.blog.core.repository.OgContentsRepository
import me.matsumo.blog.core.ui.utils.suspendRunCatching
import me.matsumo.blog.shared.model.ArticleDetail
import me.matsumo.blog.shared.model.OgContents

class ArticleDetailViewModel(
    private val articleId: Long,
    private val articleRepository: ArticleRepository,
    private val ogContentsRepository: OgContentsRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState<ArticleDetailUiState>>(ScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            _screenState.value = suspendRunCatching {
                ArticleDetailUiState(
                    article = articleRepository.getArticleDetail(articleId),
                )
            }.fold(
                onSuccess = { ScreenState.Idle(it) },
                onFailure = { ScreenState.Error(Res.string.error_no_data) },
            )
        }
    }

    suspend fun fetchOgContents(url: String): OgContents {
        return ogContentsRepository.getOgContents(url)
    }
}

@Stable
data class ArticleDetailUiState(
    val article: ArticleDetail,
)
