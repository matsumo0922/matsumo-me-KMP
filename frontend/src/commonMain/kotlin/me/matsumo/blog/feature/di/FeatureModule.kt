package me.matsumo.blog.feature.di

import me.matsumo.blog.feature.application.privacy.PrivacyPolicyViewModel
import me.matsumo.blog.feature.application.team.TeamOfServiceViewModel
import me.matsumo.blog.feature.articledetail.ArticleDetailViewModel
import me.matsumo.blog.feature.articles.ArticlesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureModule = module {
    viewModelOf(::ArticlesViewModel)

    viewModel {
        ArticleDetailViewModel(
            articleId = it.get(),
            articleRepository = get(),
            ogContentsRepository = get()
        )
    }

    viewModel {
        TeamOfServiceViewModel(
            appName = it.get(),
            applicationRepository = get(),
        )
    }

    viewModel {
        PrivacyPolicyViewModel(
            appName = it.get(),
            applicationRepository = get(),
        )
    }
}
