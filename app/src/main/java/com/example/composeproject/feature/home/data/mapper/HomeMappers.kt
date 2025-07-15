package com.example.composeproject.feature.home.data.mapper

import javax.inject.Inject

data class HomeMappers @Inject constructor(
    val homeVerticalProductsMapper: HomeVerticalProductsMapper,
    val homeSuggestedProductsMapper: HomeSuggestedProductsMapper,
    val suggestedProductDbMapper: SuggestedProductDbMapper,
    val verticalProductDbMapper: VerticalProductDbMapper
)
