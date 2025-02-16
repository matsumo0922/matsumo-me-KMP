package repository.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import repository.ArticleRepository

val repositoryModule = module {
    singleOf(::ArticleRepository)
}
