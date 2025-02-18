package me.matsumo.blog.feature.articledetail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.annotator.annotatorSettings
import com.mikepenz.markdown.annotator.buildMarkdownAnnotatedString
import com.mikepenz.markdown.compose.components.MarkdownComponent
import com.mikepenz.markdown.compose.components.MarkdownComponentModel
import com.mikepenz.markdown.compose.components.MarkdownComponents
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownText
import com.mikepenz.markdown.compose.elements.highlightedCodeBlock
import com.mikepenz.markdown.compose.elements.highlightedCodeFence
import com.mikepenz.markdown.utils.getUnescapedTextInNode
import io.github.aakira.napier.Napier
import me.matsumo.blog.core.theme.bold
import me.matsumo.blog.core.ui.LinkCard
import me.matsumo.blog.core.ui.utils.suspendRunCatching
import me.matsumo.blog.shared.model.OgContents
import org.intellij.markdown.ast.getTextInNode

class CustomMarkdownComponents(
    private val onOgContentsRequested: suspend (url: String) -> OgContents,
) {

    private val heading1: MarkdownComponent = {
        Text(
            modifier = Modifier.padding(top = 48.dp, bottom = 24.dp),
            text = getHeadingString(it, MaterialTheme.typography.headlineMedium),
        )
    }

    private val heading2: MarkdownComponent = {
        Text(
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
            text = getHeadingString(it, MaterialTheme.typography.headlineSmall),
        )
    }

    private val heading3: MarkdownComponent = {
        Text(
            modifier = Modifier.padding(top = 24.dp, bottom = 12.dp),
            text = getHeadingString(it, MaterialTheme.typography.titleLarge),
        )
    }

    private val heading4: MarkdownComponent = {
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = getHeadingString(it, MaterialTheme.typography.titleMedium),
        )
    }

    private val heading5: MarkdownComponent = {
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = getHeadingString(it, MaterialTheme.typography.titleSmall),
        )
    }

    private val heading6: MarkdownComponent = {
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = getHeadingString(it, MaterialTheme.typography.titleSmall),
        )
    }

    private val paragraph: MarkdownComponent = {
        val annotatorSettings = annotatorSettings()
        val styledText = buildAnnotatedString {
            pushStyle(it.typography.paragraph.toSpanStyle())
            buildMarkdownAnnotatedString(content = it.content, node = it.node, annotatorSettings = annotatorSettings)
            pop()
        }
        val text = it.node.getUnescapedTextInNode(it.content)
        var isFailedToShowLinkCard by rememberSaveable { mutableStateOf(false) }

        if (text.startsWith("http") && !isFailedToShowLinkCard) {
            LinkCard(
                modifier = Modifier.fillMaxWidth(),
                url = text,
                onOgContentsRequested = {
                    suspendRunCatching { onOgContentsRequested.invoke(it) }.onFailure { error ->
                        Napier.e(error) { "Failed to get OG contents: $text" }
                        isFailedToShowLinkCard = true
                    }.getOrNull()
                }
            )
        } else {
            MarkdownText(
                modifier = Modifier.fillMaxWidth(),
                content = styledText,
                style = it.typography.paragraph,
            )
        }
    }

    @Composable
    private fun getHeadingString(model: MarkdownComponentModel, style: TextStyle): AnnotatedString {
        val text = model.node.getTextInNode(model.content)
        val sharp = text.takeWhile { it == '#' }

        return buildAnnotatedString {
            withStyle(style.bold().copy(color = MaterialTheme.colorScheme.onSurface).toSpanStyle()) {
                append(text)
            }

            addStyle(style.bold().copy(color = MaterialTheme.colorScheme.primary).toSpanStyle(), 0, sharp.length)
        }
    }

    companion object {

        @Composable
        fun build(
            onOgContentsRequested: suspend (url: String) -> OgContents,
        ): MarkdownComponents {
            CustomMarkdownComponents(
                onOgContentsRequested = onOgContentsRequested,
            ).also {
                return markdownComponents(
                    heading1 = it.heading1,
                    heading2 = it.heading2,
                    heading3 = it.heading3,
                    heading4 = it.heading4,
                    heading5 = it.heading5,
                    heading6 = it.heading6,
                    paragraph = it.paragraph,
                    codeBlock = highlightedCodeBlock,
                    codeFence = highlightedCodeFence,
                )
            }
        }
    }
}
