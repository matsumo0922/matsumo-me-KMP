package me.matsumo.blog.core.repository.di

import me.matsumo.blog.core.repository.ApplicationRepository
import me.matsumo.blog.core.repository.ArticleRepository
import me.matsumo.blog.core.repository.OgContentsRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::ArticleRepository)
    factoryOf(::ApplicationRepository)
    factoryOf(::OgContentsRepository)
}
