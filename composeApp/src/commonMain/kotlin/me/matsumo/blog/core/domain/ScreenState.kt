package me.matsumo.blog.core.domain

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.StringResource

@Stable
sealed interface ScreenState<out T> {
    data object Loading : ScreenState<Nothing>

    data class Error(
        val message: StringResource,
        val retryTitle: StringResource? = null,
    ) : ScreenState<Nothing>

    data class Idle<T>(
        var data: T,
    ) : ScreenState<T>
}

fun <T> ScreenState<T>.updateWhenIdle(action: (T) -> T): ScreenState<T> = if (this is ScreenState.Idle) ScreenState.Idle(action(data)) else this

fun <T> StateFlow<ScreenState<T>>.updateWhenIdle(action: (T) -> T): ScreenState<T> = this.value.updateWhenIdle(action)
