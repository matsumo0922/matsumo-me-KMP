package me.matsumo.blog.core.utils

import io.ktor.util.*
import me.matsumo.blog.core.repository.di.repositoryModule
import me.matsumo.blog.core.utils.di.utilsModule
import org.koin.compose.getKoin
import org.koin.core.Koin
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(repositoryModule)
        modules(utilsModule)
    }
}

fun initKoinIfNeeded() {
    runCatching {
        initKoin()
    }
}
