package com.example.composeproject.feature.home.presentation

import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel

data class HomeState(
    val verticalProducts: List<VerticalProductsUiModel> = emptyList(),
)