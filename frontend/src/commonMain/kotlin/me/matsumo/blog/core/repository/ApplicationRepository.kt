package me.matsumo.blog.core.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.matsumo.blog.core.ui.utils.IO
import me.matsumo.blog.shared.ApplicationGist

class ApplicationRepository(
    private val httpClient: HttpClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun getPrivacyPolicy(gist: ApplicationGist) = withContext(ioDispatcher) {
        httpClient.get(gist.privacyPolicy).bodyAsText()
    }

    suspend fun getTermOfService(gist: ApplicationGist) = withContext(ioDispatcher) {
        httpClient.get(gist.teamOfService).bodyAsText()
    }

    fun getApplicationGist(appName: String) = when (appName.lowercase()) {
        "kanade" -> ApplicationGist.Kanade
        "pixiview" -> ApplicationGist.Fanbox
        else -> ApplicationGist.Almighty
    }
}
