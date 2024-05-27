package me.matsumo.blog.core.repository

import io.github.xxfast.kstore.KStore
import me.matsumo.blog.core.model.ThemeColorConfig
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

interface UserDataRepository {
    val userData: Flow<UserData>

    suspend fun setDefault()
    suspend fun setAgreedPrivacyPolicy(isAgreed: Boolean)
    suspend fun setAgreedTermsOfService(isAgreed: Boolean)
    suspend fun setThemeConfig(themeConfig: ThemeConfig)
    suspend fun setThemeColorConfig(themeColorConfig: ThemeColorConfig)
}

class UserDataRepositoryImpl(
    private val userDataStore: KStore<UserData>,
)  : UserDataRepository {

    override val userData: Flow<UserData> = userDataStore.updates.map {
        it ?: UserData.default()
    }

    override suspend fun setDefault() {
        userDataStore.set(UserData.default())
    }

    override suspend fun setAgreedPrivacyPolicy(isAgreed: Boolean) {
        userData.firstOrNull()?.let {
            userDataStore.set(it.copy(isAgreedPrivacyPolicy = isAgreed))
        }
    }

    override suspend fun setAgreedTermsOfService(isAgreed: Boolean) {
        userData.firstOrNull()?.let {
            userDataStore.set(it.copy(isAgreedTermsOfService = isAgreed))
        }
    }

    override suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        userData.firstOrNull()?.let {
            userDataStore.set(it.copy(themeConfig = themeConfig))
        }
    }

    override suspend fun setThemeColorConfig(themeColorConfig: ThemeColorConfig) {
        userData.firstOrNull()?.let {
            userDataStore.set(it.copy(themeColorConfig = themeColorConfig))
        }
    }
}
