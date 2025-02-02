package me.matsumo.blog.app

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.domain.ThemeConfig
import me.matsumo.blog.core.theme.isSystemInDarkThemeUnSafe

class BlogViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(
        BlogUiState(
            theme = ThemeConfig.System,
        )
    )

    val uiState = _uiState.asStateFlow()

    fun toggleTheme() {
        val isDark = when (uiState.value.theme) {
            ThemeConfig.System -> isSystemInDarkThemeUnSafe()
            ThemeConfig.Light -> false
            ThemeConfig.Dark -> true
        }

        setTheme(if (isDark) ThemeConfig.Light else ThemeConfig.Dark)
    }

    fun setTheme(theme: ThemeConfig) {
        _uiState.value = uiState.value.copy(theme = theme)
    }
}

@Stable
data class BlogUiState(
    val theme: ThemeConfig,
)
