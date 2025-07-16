package com.example.composeproject.feature.detail

import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel

data class DetailState(
    val productId: String = "",
    val productName: String = "",
    val productAttribute: String = "",
    val productImageUrl: String = "",
    val productPrice: Double = 0.0,
    val productPriceText: String = "",
    val basketItem: BasketItemUiModel? = null,
    val basketTotal: Double = 0.0
)

