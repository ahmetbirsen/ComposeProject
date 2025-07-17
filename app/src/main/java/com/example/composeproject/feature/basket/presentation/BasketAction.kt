package com.example.composeproject.feature.basket.presentation

import com.example.composeproject.designsysytem.components.DialogType

sealed interface BasketAction {
    data class AddToBasket(
        val productId: String, 
        val productName: String, 
        val productImageUrl: String, 
        val productPrice: Double, 
        val productPriceText: String
    ) : BasketAction
    data class RemoveFromBasket(val productId: String) : BasketAction
    data class ShowDialog(val dialogType: DialogType) : BasketAction
    data object HideDialog : BasketAction
    data object ClearBasket : BasketAction
    data object CompleteOrder : BasketAction
}