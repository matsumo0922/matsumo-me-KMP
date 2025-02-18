package repository.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import repository.ArticleRepository
import repository.OgContentsRepository

val repositoryModule = module {
    singleOf(::ArticleRepository)
    singleOf(::OgContentsRepository)
}
