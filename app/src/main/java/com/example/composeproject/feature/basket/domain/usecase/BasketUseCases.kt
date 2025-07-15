package com.example.composeproject.feature.basket.domain.usecase

import com.example.composeproject.feature.basket.domain.BasketRepository

data class BasketUseCases(
    val addToBasket: AddToBasketUseCase,
    val removeFromBasket: RemoveFromBasketUseCase,
    val getBasketItems: GetBasketItemsUseCase,
    val getBasketTotal: GetBasketTotalUseCase
) 