package com.example.composeproject.feature.home.data.mapper

import com.example.composeproject.core.BaseMapper
import com.example.composeproject.feature.home.data.model.VerticalProductsResponse
import com.example.composeproject.feature.home.domain.model.HomeVerticalProductsUiModel
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel
import javax.inject.Inject


class HomeVerticalProductsMapper @Inject constructor() : BaseMapper() {

    fun map(response: VerticalProductsResponse?): HomeVerticalProductsUiModel {
        return HomeVerticalProductsUiModel(
            id = response?.id.orEmpty(),
            name = response?.name.orEmpty(),
            productCount = response?.productCount?.toDouble() ?: -1.0,
            products = ArrayList(response?.products?.map {
                VerticalProductsUiModel(
                    id = it.id.orEmpty(),
                    name = it.name.orEmpty(),
                    attribute = it.attribute.orEmpty(),
                    imageURL = it.imageURL.orEmpty(),
                    price = it.price ?: -1.0,
                    priceText = it.priceText.orEmpty()
                )
            } ?: emptyList())
        )
    }
}