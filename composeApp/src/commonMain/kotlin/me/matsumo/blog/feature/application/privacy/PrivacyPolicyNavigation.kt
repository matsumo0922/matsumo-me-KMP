package me.matsumo.blog.feature.application.privacy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import me.matsumo.blog.core.domain.Destinations

fun NavGraphBuilder.privacyPolicyScreen() {
    composable<Destinations.PrivacyPolicy> {
        PrivacyPolicyRoute(
            modifier = Modifier.fillMaxSize(),
            appName = it.toRoute<Destinations.PrivacyPolicy>().appName,
        )
    }
}
