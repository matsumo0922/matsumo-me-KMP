package repository.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import repository.ArticleRepository

val repositoryModule = module {
    factoryOf(::ArticleRepository)
}
