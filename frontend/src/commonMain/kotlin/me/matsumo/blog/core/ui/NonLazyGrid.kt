package me.matsumo.blog.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
        // 横・縦の間隔をピクセル単位に変換
        val horizontalSpacingPx = horizontalSpacing.roundToPx()
        val verticalSpacingPx = verticalSpacing.roundToPx()

        // 横方向の余白分を除いた全幅から各セルの幅を算出
        val totalHorizontalSpacing = horizontalSpacingPx * (columns - 1)
        val cellWidth = (constraints.maxWidth - totalHorizontalSpacing) / columns

        // 各アイテムをセル幅に固定して測定する
        val itemConstraints = constraints.copy(minWidth = cellWidth, maxWidth = cellWidth)
        val placeables = measurables.map { measurable ->
            measurable.measure(itemConstraints)
        }

        // アイテムを行ごとにグループ化する
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

        // 全体の高さを、各行の最大高さと縦方向の間隔から算出する
        val totalHeight = rows.sumOf { row -> row.maxOf { it.height } } +
                verticalSpacingPx * (rows.size - 1)

        // レイアウトのサイズを決定
        layout(constraints.maxWidth, totalHeight) {
            var yPosition = 0
            rows.forEach { row ->
                // 現在の行の最大高さを取得
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
