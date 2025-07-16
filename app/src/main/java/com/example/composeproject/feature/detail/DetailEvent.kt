package com.example.composeproject.feature.detail

sealed interface DetailEvent {
    data class NavigateToDetail(val productId: String, val productName: String, val productAttribute: String, val productImageUrl: String, val productPrice: Double, val productPriceText: String) : DetailEvent
    data class AddToBasket(val productId: String, val productName: String, val productImageUrl: String, val productPrice: Double, val productPriceText: String) : DetailEvent
    data class RemoveFromBasket(val productId: String) : DetailEvent
    data object NavigateBack : DetailEvent
}