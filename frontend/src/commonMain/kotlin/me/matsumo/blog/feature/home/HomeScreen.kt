package me.matsumo.blog.feature.home

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.matsumo.blog.feature.home.components.HomeAboutSection
import me.matsumo.blog.feature.home.components.HomeHelloSection

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                HomeHelloSection(
                    modifier = Modifier
                        .height(maxHeight)
                        .fillMaxWidth(),
                )
            }

            item {
                HomeAboutSection(
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
