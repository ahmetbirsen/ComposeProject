package com.example.composeproject.di

import com.example.composeproject.feature.basket.domain.usecase.BasketUseCases
import com.example.composeproject.feature.home.domain.usecase.HomeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideHomeUseCases(): HomeUseCases = mockk()

    @Provides
    @Singleton
    fun provideBasketUseCases(): BasketUseCases = mockk()
} 