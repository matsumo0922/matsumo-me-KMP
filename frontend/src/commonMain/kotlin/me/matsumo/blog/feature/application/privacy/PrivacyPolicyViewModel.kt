package me.matsumo.blog.feature.application.privacy

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.error_network
import me.matsumo.blog.core.domain.ScreenState
import me.matsumo.blog.core.repository.ApplicationRepository
import me.matsumo.blog.core.ui.utils.suspendRunCatching

class PrivacyPolicyViewModel(
    private val appName: String,
    private val applicationRepository: ApplicationRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState<PrivacyPolicyUiState>>(ScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            _screenState.value = suspendRunCatching {
                val gist = applicationRepository.getApplicationGist(appName)
                val content = applicationRepository.getPrivacyPolicy(gist)

                PrivacyPolicyUiState(
                    content = content,
                )
            }.fold(
                onSuccess = { ScreenState.Idle(it) },
                onFailure = { ScreenState.Error(Res.string.error_network) },
            )
        }
    }
}

@Stable
data class PrivacyPolicyUiState(
    val content: String,
)
