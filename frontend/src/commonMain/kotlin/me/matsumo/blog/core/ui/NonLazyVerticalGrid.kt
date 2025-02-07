package me.matsumo.blog.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NonLazyVerticalGrid(
    columns: Int,
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp = 0.dp,
    verticalSpacing: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val horizontalSpacingPx = horizontalSpacing.roundToPx()
        val verticalSpacingPx = verticalSpacing.roundToPx()

        val totalHorizontalSpacing = horizontalSpacingPx * (columns - 1)
        val cellWidth = (constraints.maxWidth - totalHorizontalSpacing) / columns

        val itemConstraints = constraints.copy(minWidth = cellWidth, maxWidth = cellWidth)
        val placeables = measurables.map { measurable ->
            measurable.measure(itemConstraints)
        }

        val rows = mutableListOf<List<androidx.compose.ui.layout.Placeable>>()
        var currentRow = mutableListOf<androidx.compose.ui.layout.Placeable>()
        for (placeable in placeables) {
            if (currentRow.size == columns) {
                rows.add(currentRow)
                currentRow = mutableListOf()
            }
            currentRow.add(placeable)
        }
        if (currentRow.isNotEmpty()) {
            rows.add(currentRow)
        }

        val totalHeight = rows.sumOf { row -> row.maxOf { it.height } } + verticalSpacingPx * (rows.size - 1)

        layout(constraints.maxWidth, totalHeight) {
            var yPosition = 0

            rows.forEach { row ->
                val rowHeight = row.maxOf { it.height }
                var xPosition = 0

                row.forEach { placeable ->
                    placeable.placeRelative(x = xPosition, y = yPosition)
                    xPosition += cellWidth + horizontalSpacingPx
                }

                yPosition += rowHeight + verticalSpacingPx
            }
        }
    }
}
