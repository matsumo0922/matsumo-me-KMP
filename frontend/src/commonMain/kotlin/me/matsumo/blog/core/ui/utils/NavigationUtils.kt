package me.matsumo.blog.core.ui.utils

import androidx.navigation.NavController
import me.matsumo.blog.core.domain.Destinations

fun <T: Destinations> NavController.navigateInclusive(destination: T) {
    navigate(destination) {
        popUpTo(destination) {
            inclusive = true
        }
    }
}
