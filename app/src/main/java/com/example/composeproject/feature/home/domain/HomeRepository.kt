package com.example.composeproject.feature.home.domain

import com.example.composeproject.core.model.RestResult
import com.example.composeproject.feature.home.domain.model.HomeSuggestedProductsUiModel
import com.example.composeproject.feature.home.domain.model.HomeVerticalProductsUiModel
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getVerticalProducts(): RestResult<HomeVerticalProductsUiModel>

    suspend fun getSuggestedProducts(): RestResult<HomeSuggestedProductsUiModel>

    suspend fun saveSuggestedProductsToDatabase(suggestedProducts: List<SuggestedProductUiModel>)

    fun getSuggestedProductsFromDatabase(): Flow<List<SuggestedProductUiModel>>

    suspend fun saveVerticalProductsToDatabase(verticalProducts: List<VerticalProductsUiModel>)

    fun getVerticalProductsFromDatabase(): Flow<List<VerticalProductsUiModel>>
}