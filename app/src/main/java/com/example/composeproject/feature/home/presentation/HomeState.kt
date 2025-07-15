package com.example.composeproject.feature.home.presentation

import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel

data class HomeState(
    val verticalProducts: List<VerticalProductsUiModel> = emptyList(),
    val suggestedProducts: List<SuggestedProductUiModel> = emptyList(),
    val basketItems: List<BasketItemUiModel> = emptyList(),
    val basketTotal: Double = 0.0
)