package me.matsumo.blog.core.datastore.di

import de.jensklingenberg.ktorfit.Ktorfit
import me.matsumo.blog.core.datastore.ArticleMapper
import me.matsumo.blog.core.datastore.createArticleApi
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val datastoreModule = module {
    factory {
        val ktorfit = Ktorfit.Builder()
            .baseUrl("https://matsumo-me-api.fly.dev/api/")
            .build()

        ktorfit.createArticleApi()
    }

    factoryOf(::ArticleMapper)
}
