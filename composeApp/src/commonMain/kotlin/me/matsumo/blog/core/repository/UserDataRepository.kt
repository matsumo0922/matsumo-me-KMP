package me.matsumo.blog.core.repository

import me.matsumo.blog.core.model.ThemeColorConfig
import me.matsumo.blog.core.model.ThemeConfig
import me.matsumo.blog.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>

    suspend fun setDefault()
    suspend fun setAgreedPrivacyPolicy(isAgreed: Boolean)
    suspend fun setAgreedTermsOfService(isAgreed: Boolean)
    suspend fun setThemeConfig(themeConfig: ThemeConfig)
    suspend fun setThemeColorConfig(themeColorConfig: ThemeColorConfig)
}

/*class UserDataRepositoryImpl(
    private val userDataStore: UserDataStore,
) : UserDataRepository {

    override val userData: Flow<UserData> = userDataStore.userData

    override suspend fun setDefault() {
        userDataStore.setDefault()
    }

    override suspend fun setAgreedPrivacyPolicy(isAgreed: Boolean) {
        userDataStore.setAgreedPrivacyPolicy(isAgreed)
    }

    override suspend fun setAgreedTermsOfService(isAgreed: Boolean) {
        userDataStore.setAgreedTermsOfService(isAgreed)
    }

    override suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        userDataStore.setThemeConfig(themeConfig)
    }

    override suspend fun setThemeColorConfig(themeColorConfig: ThemeColorConfig) {
        userDataStore.setThemeColorConfig(themeColorConfig)
    }
}*/
