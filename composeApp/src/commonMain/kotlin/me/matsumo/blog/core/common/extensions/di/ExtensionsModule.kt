package me.matsumo.blog.core.common.extensions.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
val extensionsModule = module {
    single<CoroutineDispatcher> {
        Dispatchers.Default.limitedParallelism(100)
    }

    factory<CoroutineScope> {
        CoroutineScope(get<CoroutineDispatcher>() + SupervisorJob())
    }
}
