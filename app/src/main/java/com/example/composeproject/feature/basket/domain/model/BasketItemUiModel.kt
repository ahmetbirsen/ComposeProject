package com.example.composeproject.feature.basket.domain.model

data class BasketItemUiModel(
    val id: String = "",
    val name: String = "",
    val imageURL: String = "",
    val price: Double = 0.0,
    val priceText: String = "",
    val quantity: Int = 0,
    val totalPrice: Double = 0.0
) 