package com.example.composeproject.feature.home.presentation

import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel

data class HomeState(
    val verticalProducts: List<VerticalProductsUiModel> = emptyList(),
    val suggestedProducts: List<SuggestedProductUiModel> = emptyList()
)