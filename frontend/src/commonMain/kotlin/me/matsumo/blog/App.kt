package me.matsumo.blog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.matsumo.blog.core.theme.BlogTheme

@Composable
internal fun App() {
    BlogTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(100) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Items $it"
                )
            }
        }
    }
}
