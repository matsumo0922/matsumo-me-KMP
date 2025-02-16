package usecase.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import usecase.GetArticleEntitiesUseCase
import usecase.UpdateMarkdownArticleUseCase
import usecase.UpdateQiitaArticleUseCase
import usecase.UpdateZennArticleUseCase

val useCaseModule = module {
    factoryOf(::UpdateQiitaArticleUseCase)
    factoryOf(::UpdateZennArticleUseCase)
    factoryOf(::UpdateMarkdownArticleUseCase)
    factoryOf(::GetArticleEntitiesUseCase)
}
