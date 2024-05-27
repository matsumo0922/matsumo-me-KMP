package me.matsumo.blog.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import matsumo_me.composeapp.generated.resources.Res
import matsumo_me.composeapp.generated.resources.error_network
import me.matsumo.blog.core.common.extensions.suspendRunCatching
import me.matsumo.blog.core.model.Article
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.model.UserData
import me.matsumo.blog.core.repository.BlogRepository
import me.matsumo.blog.core.repository.UserDataRepository

class HomeViewModel(
    private val userDataRepository: UserDataRepository,
    private val blogRepository: BlogRepository,
): ViewModel() {
    private var _screenState = MutableStateFlow<ScreenState<HomeUiState>>(ScreenState.Loading)

    val screenState = _screenState.asStateFlow()

    fun fetch() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            _screenState.value = suspendRunCatching {
                HomeUiState(
                    userData = userDataRepository.userData.first(),
                    articles = blogRepository.getArticles(),
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
}

@Stable
data class HomeUiState(
    val userData: UserData,
    val articles: List<Article>,
)
