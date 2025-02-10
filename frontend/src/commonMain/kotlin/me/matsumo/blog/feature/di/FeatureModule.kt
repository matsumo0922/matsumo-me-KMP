package me.matsumo.blog.feature.di

import me.matsumo.blog.feature.articles.ArticlesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureModule = module {
    viewModelOf(::ArticlesViewModel)
}
