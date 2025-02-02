package me.matsumo.blog.core.domain

sealed interface WindowWidthSize {
    data object Compact : WindowWidthSize
    data object Medium : WindowWidthSize
    data object Expanded : WindowWidthSize
}

fun calculateWindowWidthSize(width: Int): WindowWidthSize {
    return when {
        width < 680 -> WindowWidthSize.Compact
        width < 960 -> WindowWidthSize.Medium
        else -> WindowWidthSize.Expanded
    }
}

fun WindowWidthSize.isBiggerThan(other: WindowWidthSize): Boolean {
    return this.getInternalNumber() > other.getInternalNumber()
}

fun WindowWidthSize.isSmallerThan(other: WindowWidthSize): Boolean {
    return this.getInternalNumber() < other.getInternalNumber()
}

private fun WindowWidthSize.getInternalNumber(): Int {
    return when (this) {
        is WindowWidthSize.Compact -> 1
        is WindowWidthSize.Medium -> 2
        is WindowWidthSize.Expanded -> 3
    }
}
