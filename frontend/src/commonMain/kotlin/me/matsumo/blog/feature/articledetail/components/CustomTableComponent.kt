package me.matsumo.blog.feature.articledetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mikepenz.markdown.annotator.annotatorSettings
import com.mikepenz.markdown.annotator.buildMarkdownAnnotatedString
import com.mikepenz.markdown.compose.LocalImageTransformer
import com.mikepenz.markdown.compose.LocalMarkdownColors
import com.mikepenz.markdown.compose.LocalMarkdownDimens
import com.mikepenz.markdown.compose.elements.MarkdownDivider
import com.mikepenz.markdown.compose.elements.MarkdownText
import com.mikepenz.markdown.compose.elements.material.MarkdownBasicText
import com.mikepenz.markdown.utils.getUnescapedTextInNode
import org.intellij.markdown.IElementType
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.gfm.GFMElementTypes.HEADER
import org.intellij.markdown.flavours.gfm.GFMElementTypes.ROW
import org.intellij.markdown.flavours.gfm.GFMTokenTypes.CELL
import org.intellij.markdown.flavours.gfm.GFMTokenTypes.TABLE_SEPARATOR

@Composable
fun CustomTableComponent(
    content: String,
    node: ASTNode,
    style: TextStyle,
) {
    val tableMaxWidth = LocalMarkdownDimens.current.tableMaxWidth
    val tableCornerSize = LocalMarkdownDimens.current.tableCornerSize

    val backgroundCodeColor = LocalMarkdownColors.current.tableBackground

    Box(
        modifier = Modifier
            .background(backgroundCodeColor, RoundedCornerShape(tableCornerSize))
            .widthIn(max = tableMaxWidth)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            node.children.forEach {
                when (it.type) {
                    HEADER -> {
                        MarkdownTableHeader(
                            content = content,
                            header = it,
                            style = style
                        )
                    }

                    ROW -> {
                        MarkdownTableRow(
                            content = content,
                            header = it,
                            style = style
                        )
                    }

                    TABLE_SEPARATOR -> MarkdownDivider()
                }
            }
        }
    }
}

@Composable
fun MarkdownTableHeader(
    content: String,
    header: ASTNode,
    style: TextStyle,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
) {
    val tableCellPadding = LocalMarkdownDimens.current.tableCellPadding
    val annotatorSettings = annotatorSettings()

    Row(
        modifier = Modifier.height(IntrinsicSize.Max),
        verticalAlignment = verticalAlignment,
    ) {
        header.children.forEach {
            when (it.type) {
                CELL -> {
                    MarkdownBasicText(
                        modifier = Modifier
                            .padding(tableCellPadding)
                            .weight(1f),
                        text = content.buildMarkdownAnnotatedString(
                            textNode = it,
                            style = style.copy(fontWeight = FontWeight.Bold),
                            annotatorSettings = annotatorSettings,
                        ),
                        textAlign = TextAlign.Center,
                        style = style.copy(fontWeight = FontWeight.Bold),
                        color = LocalMarkdownColors.current.tableText,
                    )
                }
            }
        }
    }
}

@Composable
fun MarkdownTableRow(
    content: String,
    header: ASTNode,
    style: TextStyle,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
) {
    val transformer = LocalImageTransformer.current
    val tableCellPadding = LocalMarkdownDimens.current.tableCellPadding
    val annotatorSettings = annotatorSettings()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = verticalAlignment,
    ) {
        header.children.filter { it.type == CELL }.forEach { cell ->
            val hasOneImage = cell.children.all { it.type == MarkdownElementTypes.IMAGE }.takeIf { cell.children.size == 1 } ?: false
            val availableLink = cell.children.firstOrNull()?.let { child ->
                child.findChildOfTypeRecursive(MarkdownElementTypes.LINK_DESTINATION)?.getUnescapedTextInNode(content)
            }
            val imageData = if (hasOneImage && !availableLink.isNullOrBlank()) transformer.transform(availableLink) else null

            if (imageData != null) {
                Image(
                    modifier = Modifier
                        .padding(tableCellPadding)
                        .weight(1f),
                    painter = imageData.painter,
                    contentDescription = imageData.contentDescription,
                    alignment = imageData.alignment,
                    contentScale = imageData.contentScale,
                    alpha = imageData.alpha,
                    colorFilter = imageData.colorFilter
                )
            } else {
                MarkdownText(
                    modifier = Modifier
                        .padding(tableCellPadding)
                        .weight(1f),
                    content = content.buildMarkdownAnnotatedString(
                        textNode = cell,
                        style = style,
                        annotatorSettings = annotatorSettings,
                    ),
                    style = style.copy(color = LocalMarkdownColors.current.tableText),
                )
            }
        }
    }
}

fun ASTNode.findChildOfTypeRecursive(type: IElementType): ASTNode? {
    children.forEach {
        if (it.type == type) {
            return it
        } else {
            val found = it.findChildOfTypeRecursive(type)
            if (found != null) {
                return found
            }
        }
    }
    return null
}
