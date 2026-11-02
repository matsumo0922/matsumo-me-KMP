package me.matsumo.blog.feature.application.team

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import me.matsumo.blog.core.domain.Destinations

fun NavGraphBuilder.teamOfServiceScreen() {
    composable<Destinations.TermsOfService> {
        TeamOfServiceRoute(
            modifier = Modifier.fillMaxSize(),
            appName = it.toRoute<Destinations.TermsOfService>().appName,
        )
    }
}
