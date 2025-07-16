package com.example.composeproject.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Market : Routes

    @Serializable
    data object Home : Routes

    @Serializable
    data class Detail(
        val productId: String,
        val name: String,
        val imageUrl: String,
        val price: Double,
        val priceText: String,
        val attribute: String
    ) : Routes

    @Serializable
    data object Basket : Routes
}