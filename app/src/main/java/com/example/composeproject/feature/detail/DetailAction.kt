package com.example.composeproject.feature.detail

sealed interface DetailAction {
    data object OnCloseClick : DetailAction
    data object OnBasketBoxClick: DetailAction
    data class OnAddToBasket(
        val productId: String,
        val name: String,
        val imageURL: String,
        val price: Double,
        val priceText: String
    ): DetailAction
    data class OnRemoveFromBasket(
        val productId: String
    ): DetailAction
    data class AddToBasket(val productId: String, val productName: String, val productImageUrl: String, val productPrice: Double, val productPriceText: String) : DetailAction
    data class RemoveFromBasket(val productId: String) : DetailAction
}