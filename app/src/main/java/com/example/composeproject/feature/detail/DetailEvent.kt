package com.example.composeproject.feature.detail

sealed interface DetailEvent {
    data object Event : DetailEvent
}