package com.example.composeproject.feature.home.domain.di

import com.example.composeproject.feature.home.domain.usecase.GetSuggestedProductsUseCase
import com.example.composeproject.feature.home.domain.usecase.GetVerticalProductsUseCase
import com.example.composeproject.feature.home.domain.usecase.HomeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HomeUseCaseModule {

    @Provides
    fun provideHomeUseCases(
        getVerticalProducts: GetVerticalProductsUseCase,
        getSuggestedProducts: GetSuggestedProductsUseCase
    ): HomeUseCases {
        return HomeUseCases(
            getVerticalProducts = getVerticalProducts,
            getSuggestedProducts = getSuggestedProducts
        )
    }
}