package com.example.composeproject.feature.detail

sealed interface DetailAction {
    data class AddToBasket(val productId: String, val productName: String, val productImageUrl: String, val productPrice: Double, val productPriceText: String) : DetailAction
    data class RemoveFromBasket(val productId: String) : DetailAction
    data object NavigateBack : DetailAction
}