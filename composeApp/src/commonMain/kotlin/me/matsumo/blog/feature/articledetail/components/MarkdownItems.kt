package me.matsumo.blog.feature.articledetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.compose.LocalImageTransformer
import com.mikepenz.markdown.compose.LocalMarkdownAnimations
import com.mikepenz.markdown.compose.LocalMarkdownAnnotator
import com.mikepenz.markdown.compose.LocalMarkdownColors
import com.mikepenz.markdown.compose.LocalMarkdownComponents
import com.mikepenz.markdown.compose.LocalMarkdownDimens
import com.mikepenz.markdown.compose.LocalMarkdownExtendedSpans
import com.mikepenz.markdown.compose.LocalMarkdownPadding
import com.mikepenz.markdown.compose.LocalMarkdownTypography
import com.mikepenz.markdown.compose.LocalReferenceLinkHandler
import com.mikepenz.markdown.compose.MarkdownElement
import com.mikepenz.markdown.compose.components.MarkdownComponents
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.ImageTransformer
import com.mikepenz.markdown.model.MarkdownAnimations
import com.mikepenz.markdown.model.MarkdownAnnotator
import com.mikepenz.markdown.model.MarkdownColors
import com.mikepenz.markdown.model.MarkdownDimens
import com.mikepenz.markdown.model.MarkdownExtendedSpans
import com.mikepenz.markdown.model.MarkdownPadding
import com.mikepenz.markdown.model.MarkdownTypography
import com.mikepenz.markdown.model.NoOpImageTransformerImpl
import com.mikepenz.markdown.model.ReferenceLinkHandlerImpl
import com.mikepenz.markdown.model.markdownAnimations
import com.mikepenz.markdown.model.markdownAnnotator
import com.mikepenz.markdown.model.markdownDimens
import com.mikepenz.markdown.model.markdownExtendedSpans
import com.mikepenz.markdown.model.markdownPadding
import com.mikepenz.markdown.utils.getUnescapedTextInNode
import me.matsumo.blog.core.theme.CONTAINER_MAX_WIDTH
import org.intellij.markdown.MarkdownElementTypes.ATX_1
import org.intellij.markdown.MarkdownElementTypes.ATX_2
import org.intellij.markdown.MarkdownElementTypes.ATX_3
import org.intellij.markdown.MarkdownElementTypes.ATX_4
import org.intellij.markdown.MarkdownElementTypes.ATX_5
import org.intellij.markdown.MarkdownElementTypes.ATX_6
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor

fun LazyListScope.markdownItems(
    content: String,
    parsedTree: ASTNode,
    markdownSettings: MarkdownSettings,
    contentPadding: PaddingValues,
) {
    items(
        items = parsedTree.children,
        key = { node ->
            (findHeading(content, node).firstOrNull()?.key ?: "node-${node.hashCode()}")
        },
    ) { node ->
        CompositionLocalProvider(
            LocalReferenceLinkHandler provides ReferenceLinkHandlerImpl(),
            LocalMarkdownPadding provides markdownSettings.padding,
            LocalMarkdownDimens provides markdownSettings.dimens,
            LocalMarkdownColors provides markdownSettings.colors,
            LocalMarkdownTypography provides markdownSettings.typography,
            LocalImageTransformer provides markdownSettings.imageTransformer,
            LocalMarkdownAnnotator provides markdownSettings.annotator,
            LocalMarkdownExtendedSpans provides markdownSettings.extendedSpans,
            LocalMarkdownComponents provides markdownSettings.components,
            LocalMarkdownAnimations provides markdownSettings.animations,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = CONTAINER_MAX_WIDTH)
                    .fillMaxWidth()
                    .padding(contentPadding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                MarkdownElement(
                    node = node,
                    components = markdownSettings.components,
                    content = content,
                )
            }
        }
    }
}

fun findHeading(
    content: String,
    node: ASTNode,
): List<MarkdownHeader> {
    val result = mutableListOf<MarkdownHeader>()

    fun traverse(node: ASTNode, index: Int) {
        val text = node.getUnescapedTextInNode(content)
        val key = "header-${node.hashCode()}"

        when (node.type) {
            ATX_1 -> result.add(MarkdownHeader(text, MarkdownHeader.MarkdownHeaderNode.H1, key, index))

            ATX_2 -> result.add(MarkdownHeader(text, MarkdownHeader.MarkdownHeaderNode.H2, key, index))

            ATX_3 -> result.add(MarkdownHeader(text, MarkdownHeader.MarkdownHeaderNode.H3, key, index))

            ATX_4 -> result.add(MarkdownHeader(text, MarkdownHeader.MarkdownHeaderNode.H4, key, index))

            ATX_5 -> result.add(MarkdownHeader(text, MarkdownHeader.MarkdownHeaderNode.H5, key, index))

            ATX_6 -> result.add(MarkdownHeader(text, MarkdownHeader.MarkdownHeaderNode.H6, key, index))

            else -> {
                // do nothing
            }
        }

        node.children.forEach { child ->
            traverse(child, index)
        }
    }

    node.children.forEachIndexed { index, child ->
        traverse(child, index)
    }

    return result
}

@Stable
data class MarkdownSettings(
    val colors: MarkdownColors,
    val typography: MarkdownTypography,
    val padding: MarkdownPadding,
    val dimens: MarkdownDimens,
    val flavour: MarkdownFlavourDescriptor,
    val imageTransformer: ImageTransformer,
    val annotator: MarkdownAnnotator,
    val extendedSpans: MarkdownExtendedSpans,
    val components: MarkdownComponents,
    val animations: MarkdownAnimations,
) {
    companion object {
        @Composable
        fun default() = MarkdownSettings(
            colors = markdownColor(),
            typography = markdownTypography(),
            padding = markdownPadding(),
            dimens = markdownDimens(),
            flavour = GFMFlavourDescriptor(),
            imageTransformer = NoOpImageTransformerImpl(),
            annotator = markdownAnnotator(),
            extendedSpans = markdownExtendedSpans(),
            components = markdownComponents(),
            animations = markdownAnimations(),
        )
    }
}

@Stable
data class MarkdownHeader(
    val content: String,
    val node: MarkdownHeaderNode,
    val key: String,
    val index: Int,
) {
    @Stable
    enum class MarkdownHeaderNode {
        H1,
        H2,
        H3,
        H4,
        H5,
        H6,
    }
}
