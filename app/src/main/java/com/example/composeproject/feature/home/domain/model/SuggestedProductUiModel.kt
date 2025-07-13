package com.example.composeproject.feature.home.domain.model

data class SuggestedProductUiModel(
    val id: String = "",
    val name: String = "",
    val imageURL: String = "",
    val price: Double = -1.0,
    val priceText: String = "",
    val shortDescription: String = "",
    val category: String = "",
    val unitPrice: Double = -1.0,
    val squareThumbnailURL: String = "",
    val status: Int? = 0,
)
