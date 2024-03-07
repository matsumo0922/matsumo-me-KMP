package me.matsumo.blog.core.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TypingText(
    typeText: List<TypeItem>,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
    delayMillis: Long = 40,
    isInfinite: Boolean = false,
    onCompleted: () -> Unit = {},
) {
    var text by remember { mutableStateOf("") }

    var cursorColor by remember { mutableStateOf(color) }
    val cursorColorAnimation by animateColorAsState(
        targetValue = cursorColor,
        animationSpec = tween(500)
    )

    LaunchedEffect(typeText) {
        do {
            text = ""

            for (item in typeText) {
                when (item) {
                    is TypeItem.Char -> text += item.char
                    is TypeItem.Delay -> delay(item.millis)
                }

                delay(delayMillis)
            }

            onCompleted.invoke()
        } while (isInfinite)
    }

    LaunchedEffect(typeText) {
        while (true) {
            cursorColor = color
            delay(500)
            cursorColor = Color.Transparent
            delay(500)
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(1.dp),
    ) {
        Text(
            text = text,
            style = style,
            color = color,
        )

        Box(
            modifier = Modifier
                .size(8.dp, style.fontSize.value.dp)
                .background(cursorColorAnimation),
        )
    }
}

fun buildTypeText(vararg items: Any): List<TypeItem> {
    val textAnimations = mutableListOf<TypeItem>()

    for (item in items) {
        when (item) {
            is Char -> textAnimations.add(TypeItem.Char(item))
            is String -> textAnimations.addAll(item.map { TypeItem.Char(it) })
            is Number -> textAnimations.add(TypeItem.Delay(item.toLong()))
        }
    }

    return textAnimations
}

sealed interface TypeItem {
    data class Char(val char: kotlin.Char) : TypeItem
    data class Delay(val millis: Long) : TypeItem
}
