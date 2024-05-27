package me.matsumo.blog.core.datastore

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import me.matsumo.blog.core.model.UserData
import org.koin.dsl.module

val dataStoreModule = module {
    single<KStore<UserData>> {
        storeOf("user_data", UserData.default())
    }
}
