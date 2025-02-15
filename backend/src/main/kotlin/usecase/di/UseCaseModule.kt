package usecase.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import usecase.UpdateQiitaArticleUseCase

val useCaseModule = module {
    factoryOf(::UpdateQiitaArticleUseCase)
}
