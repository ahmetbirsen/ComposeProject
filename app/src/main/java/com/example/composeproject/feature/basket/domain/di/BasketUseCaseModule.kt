package com.example.composeproject.feature.basket.domain.di

import com.example.composeproject.feature.basket.domain.BasketRepository
import com.example.composeproject.feature.basket.domain.usecase.AddToBasketUseCase
import com.example.composeproject.feature.basket.domain.usecase.BasketUseCases
import com.example.composeproject.feature.basket.domain.usecase.ClearBasketUseCase
import com.example.composeproject.feature.basket.domain.usecase.GetBasketItemsUseCase
import com.example.composeproject.feature.basket.domain.usecase.GetBasketTotalUseCase
import com.example.composeproject.feature.basket.domain.usecase.RemoveFromBasketUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class BasketUseCaseModule {

    @Provides
    fun provideBasketUseCases(
        addToBasket: AddToBasketUseCase,
        removeFromBasket: RemoveFromBasketUseCase,
        getBasketItems: GetBasketItemsUseCase,
        getBasketTotal: GetBasketTotalUseCase,
        clearBasket: ClearBasketUseCase
    ): BasketUseCases {
        return BasketUseCases(
            addToBasket = addToBasket,
            removeFromBasket = removeFromBasket,
            getBasketItems = getBasketItems,
            getBasketTotal = getBasketTotal,
            clearBasket = clearBasket
        )
    }
} 