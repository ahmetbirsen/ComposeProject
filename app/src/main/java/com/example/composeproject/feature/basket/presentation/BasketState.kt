package com.example.composeproject.feature.basket.presentation

import com.example.composeproject.designsysytem.components.DialogType
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel

data class BasketState(
    val basketItems: List<BasketItemUiModel> = emptyList(),
    val suggestedProducts: List<SuggestedProductUiModel> = emptyList(),
    val basketTotal: Double = 0.0,
    val showDialog: Boolean = false,
    val dialogType: DialogType? = null,
    val isButtonLoading: Boolean = false
)

