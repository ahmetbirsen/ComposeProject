package com.example.composeproject.feature.home.domain

import com.example.composeproject.core.model.RestResult
import com.example.composeproject.feature.home.domain.model.HomeSuggestedProductsUiModel
import com.example.composeproject.feature.home.domain.model.HomeVerticalProductsUiModel

interface HomeRepository {

    suspend fun getVerticalProducts(): RestResult<HomeVerticalProductsUiModel>

    suspend fun getSuggestedProducts(): RestResult<HomeSuggestedProductsUiModel>
}