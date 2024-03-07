package me.matsumo.blog.core.ui.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay

@Composable
fun AnimatedText(
    animateItems: List<TextAnimateItem>,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
    delayMillis: Long = 50,
) {
    var text by remember { mutableStateOf("") }

    LaunchedEffect(animateItems) {
        text = ""

        for (item in animateItems) {
            when (item) {
                is TextAnimateItem.Char -> text += item.char
                is TextAnimateItem.Delay -> delay(item.millis)
            }

            delay(delayMillis)
        }
    }

    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
    )
}

fun buildTextAnimateItems(vararg items: Any): List<TextAnimateItem> {
    val textAnimations = mutableListOf<TextAnimateItem>()

    for (item in items) {
        when (item) {
            is Char -> textAnimations.add(TextAnimateItem.Char(item))
            is String -> textAnimations.addAll(item.map { TextAnimateItem.Char(it) })
            is Number -> textAnimations.add(TextAnimateItem.Delay(item.toLong()))
        }
    }

    return textAnimations
}

sealed interface TextAnimateItem {
    data class Char(val char: kotlin.Char) : TextAnimateItem
    data class Delay(val millis: Long) : TextAnimateItem
}
