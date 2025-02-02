package me.matsumo.blog.core.domain

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destinations {

    @Serializable
    data object Home : Destinations

    @Serializable
    data class Articles(
        val id: String,
        val sample: String,
    ) : Destinations
}
