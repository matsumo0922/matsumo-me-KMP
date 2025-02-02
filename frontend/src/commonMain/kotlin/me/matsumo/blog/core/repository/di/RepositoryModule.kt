package me.matsumo.blog.core.repository.di

import me.matsumo.blog.core.repository.ArticleRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::ArticleRepository)
}
