package me.matsumo.blog.feature.revision

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.matsumo.blog.core.domain.Destinations

fun NavGraphBuilder.revisionScreen() {
    composable<Destinations.Revision> {
        RevisionScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}
