package com.example.composeproject.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Market : Routes

    @Serializable
    data object Home : Routes

    @Serializable
    data object Detail : Routes

    @Serializable
    data object Basket : Routes
}