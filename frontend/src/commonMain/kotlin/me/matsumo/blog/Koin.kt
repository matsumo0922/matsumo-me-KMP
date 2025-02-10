package me.matsumo.blog

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.matsumo.blog.app.BlogViewModel
import me.matsumo.blog.core.datastore.di.datastoreModule
import me.matsumo.blog.core.domain.BlogConfig
import me.matsumo.blog.core.repository.di.repositoryModule
import me.matsumo.blog.core.ui.utils.IO
import me.matsumo.blog.feature.di.featureModule
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(appModule)
        modules(datastoreModule)
        modules(repositoryModule)
        modules(featureModule)
    }
}

val appModule = module {
    viewModelOf(::BlogViewModel)

    single {
        BlogConfig(
            versionCode = BuildKonfig.VERSION_CODE.toInt(),
            versionName = BuildKonfig.VERSION_NAME,
            sendGirdApiKey = BuildKonfig.SENDGIRD_API_KEY,
        )
    }

    single<CoroutineDispatcher> {
        Dispatchers.IO.limitedParallelism(24)
    }
}
