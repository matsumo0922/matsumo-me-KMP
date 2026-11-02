package me.matsumo.blog.core.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class FixedWithEdgeSpace(
    private val count: Int,
    private val edgeSpace: Dp = 0.dp,
) : GridCells {

    init {
        require(count > 0)
    }

    override fun Density.calculateCrossAxisCellSizes(availableSize: Int, spacing: Int): List<Int> {
        val fixedCount = count + 2
        val edgeSpacing = edgeSpace.roundToPx()
        val gridSizeWithoutSpacing = availableSize - spacing * (fixedCount - 1) - edgeSpacing * 2
        val slotSize = gridSizeWithoutSpacing / count
        val remainingPixels = gridSizeWithoutSpacing % count

        return List(fixedCount) {
            if (it == 0 || it == fixedCount - 1) {
                edgeSpacing
            } else {
                slotSize + if (it - 1 < remainingPixels) 1 else 0
            }
        }
    }
}

inline fun <T> LazyGridScope.itemsWithEdgeSpace(
    @androidx.annotation.IntRange(from = 1) spanCount: Int,
    items: List<T>,
    noinline key: ((item: T) -> Any)? = null,
    noinline span: (LazyGridItemSpanScope.(item: T) -> GridItemSpan)? = null,
    noinline contentType: (item: T) -> Any? = { null },
    crossinline itemContent: @Composable LazyGridItemScope.(item: T) -> Unit,
) {
    require(spanCount > 0)

    val itemSize = items.size
    val rowCount = if (itemSize % spanCount == 0) itemSize / spanCount else itemSize / spanCount + 1

    for (index in 0..<rowCount) {
        item { Spacer(modifier = Modifier.fillMaxWidth()) }

        val childItems = items.subList(
            index * spanCount,
            minOf(index * spanCount + spanCount, itemSize),
        )

        items(childItems, key, span, contentType, itemContent)

        if (index != rowCount - 1 || itemSize % spanCount != 0) {
            item { Spacer(modifier = Modifier.fillMaxWidth()) }
        }
    }
}

inline fun <T> LazyGridScope.itemsIndexedWithEdgeSpace(
    @androidx.annotation.IntRange(from = 1) spanCount: Int,
    items: List<T>,
    noinline key: ((index: Int, item: T) -> Any)? = null,
    noinline span: (LazyGridItemSpanScope.(index: Int, item: T) -> GridItemSpan)? = null,
    noinline contentType: (index: Int, item: T) -> Any? = { _, _ -> null },
    crossinline itemContent: @Composable LazyGridItemScope.(index: Int, item: T) -> Unit,
) {
    require(spanCount > 0)

    val itemSize = items.size
    val rowCount = if (itemSize % spanCount == 0) itemSize / spanCount else itemSize / spanCount + 1

    for (index in 0..<rowCount) {
        item { Spacer(modifier = Modifier.fillMaxWidth()) }

        val childItems = items.subList(
            index * spanCount,
            minOf(index * spanCount + spanCount, itemSize),
        )

        itemsIndexed(childItems, key, span, contentType, itemContent)

        if (index != rowCount - 1 || itemSize % spanCount != 0) {
            item { Spacer(modifier = Modifier.fillMaxWidth()) }
        }
    }
}
