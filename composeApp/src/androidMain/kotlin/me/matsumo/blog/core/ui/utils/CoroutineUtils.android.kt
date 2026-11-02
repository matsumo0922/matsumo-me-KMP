package me.matsumo.blog.core.ui.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

// InjectDispatcher: wasm に Dispatchers.IO がないため、共通コードから参照する IO をプラットフォームごとに定義する
@Suppress("InjectDispatcher")
actual val Dispatchers.IO: CoroutineDispatcher
    get() = Dispatchers.IO
