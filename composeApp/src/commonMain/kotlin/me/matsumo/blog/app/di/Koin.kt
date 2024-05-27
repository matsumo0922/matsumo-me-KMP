package me.matsumo.blog.app.di

import me.matsumo.blog.core.common.extensions.di.extensionsModule
import me.matsumo.blog.core.datastore.dataStoreModule
import me.matsumo.blog.core.repository.di.repositoryModule
import me.matsumo.blog.feature.di.featureModule
import org.koin.core.KoinApplication

fun KoinApplication.applyModules() {
    // app
    modules(appModule)

    // core
    modules(repositoryModule)
    modules(dataStoreModule)
    modules(extensionsModule)

    // feature
    modules(featureModule)
}
