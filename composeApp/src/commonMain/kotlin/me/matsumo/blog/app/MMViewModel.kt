package me.matsumo.blog.app

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.matsumo.blog.core.model.ScreenState
import me.matsumo.blog.core.model.UserData
import me.matsumo.blog.core.repository.UserDataRepository

class MMViewModel(userDataRepository: UserDataRepository): ViewModel() {

    val screenState = userDataRepository.userData.map {
        ScreenState.Idle(
            MMUiState(
                userData = it,
            ),
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = ScreenState.Loading,
        started = SharingStarted.WhileSubscribed(5000),
    )
}

@Stable
data class MMUiState(
    val userData: UserData,
)
