package com.example.composeproject.feature.basket.presentation

import com.example.composeproject.core.UiText

sealed interface BasketEvent {
    data class ShowError(val message: UiText) : BasketEvent
    data object BasketCleared : BasketEvent
    data object OrderCompleted : BasketEvent
}