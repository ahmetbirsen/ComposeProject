package com.example.composeproject.feature.home.domain.usecase

data class HomeUseCases(
    val getVerticalProducts: GetVerticalProductsUseCase,
    val getSuggestedProducts: GetSuggestedProductsUseCase,
    val saveSuggestedProductsToDatabase: SaveSuggestedProductsToDatabaseUseCase,
    val saveVerticalProductsToDatabase: SaveVerticalProductsToDatabaseUseCase
)