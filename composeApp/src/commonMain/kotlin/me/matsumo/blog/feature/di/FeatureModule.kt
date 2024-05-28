package me.matsumo.blog.feature.di

import me.matsumo.blog.feature.article.ArticleViewModel
import me.matsumo.blog.feature.home.HomeViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featureModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::ArticleViewModel)
}
