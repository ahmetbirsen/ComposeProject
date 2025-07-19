package com.example.composeproject.feature.home.presentation

import com.example.composeproject.navigation.Routes

sealed interface HomeAction {
    data object OnBasketBoxClick: HomeAction
    data class OnAddToBasket(
        val productId: String,
        val name: String,
        val imageURL: String,
        val price: Double,
        val priceText: String
    ): HomeAction
    data class OnRemoveFromBasket(
        val productId: String
    ): HomeAction
    data object OnRefresh: HomeAction
    data class OnProductClick(
        val product: Routes.Detail
    ): HomeAction
}