package me.matsumo.blog.feature.article

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import matsumo_me.composeapp.generated.resources.Res
import matsumo_me.composeapp.generated.resources.error_network
import me.matsumo.blog.core.common.extensions.suspendRunCatching
import me.matsumo.blog.core.model.ArticleDetail
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.model.UserData
import me.matsumo.blog.core.model.WebMetadata
import me.matsumo.blog.core.repository.BlogRepository
import me.matsumo.blog.core.repository.UserDataRepository
import me.matsumo.blog.core.repository.WebRepository

class ArticleViewModel(
    private val userDataRepository: UserDataRepository,
    private val blogRepository: BlogRepository,
    private val webRepository: WebRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState<ArticleUiState>>(ScreenState.Loading)

    val screenState = _screenState.asStateFlow()

    fun fetch(id: String) {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            _screenState.value = suspendRunCatching {
                ArticleUiState(
                    userData = userDataRepository.userData.first(),
                    article = blogRepository.getArticle(id),
                )
            }.fold(
                onSuccess = { ScreenState.Idle(it) },
                onFailure = { ScreenState.Error(Res.string.error_network) }
            )
        }
    }

    fun setThemeConfig(themeConfig: ThemeConfig) {
        viewModelScope.launch {
            userDataRepository.setThemeConfig(themeConfig)
        }
    }

    suspend fun getWebMetadata(url: String): WebMetadata {
        return suspendRunCatching { webRepository.getWebMetadata(url) }.getOrDefault(WebMetadata.Unknown(url))
    }
}

@Stable
data class ArticleUiState(
    val userData: UserData,
    val article: ArticleDetail,
)