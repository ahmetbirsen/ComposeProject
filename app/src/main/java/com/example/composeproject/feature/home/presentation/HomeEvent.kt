package com.example.composeproject.feature.home.presentation

sealed interface HomeEvent {
    data object LoginSuccess: HomeEvent
}