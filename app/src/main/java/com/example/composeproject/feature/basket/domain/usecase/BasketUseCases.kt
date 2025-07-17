package com.example.composeproject.feature.basket.domain.usecase

data class BasketUseCases(
    val addToBasket: AddToBasketUseCase,
    val removeFromBasket: RemoveFromBasketUseCase,
    val getBasketItems: GetBasketItemsUseCase,
    val getBasketTotal: GetBasketTotalUseCase,
    val clearBasket: ClearBasketUseCase
) 