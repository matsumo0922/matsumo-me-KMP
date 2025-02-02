package me.matsumo.blog

import me.matsumo.blog.app.BlogViewModel
import me.matsumo.blog.core.datastore.di.datastoreModule
import me.matsumo.blog.core.repository.di.repositoryModule
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(appModule)
        modules(datastoreModule)
        modules(repositoryModule)
    }
}

val appModule = module {
    viewModelOf(::BlogViewModel)
}
