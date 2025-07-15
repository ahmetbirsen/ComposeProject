package com.example.composeproject.feature.basket.data.repository.di

import com.example.composeproject.feature.basket.data.repository.BasketRepositoryImpl
import com.example.composeproject.feature.basket.domain.BasketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface BasketRepositoryModule {

    @Binds
    fun bindBasketRepository(impl: BasketRepositoryImpl): BasketRepository
} 