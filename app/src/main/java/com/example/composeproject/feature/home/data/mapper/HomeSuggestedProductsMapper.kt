package com.example.composeproject.feature.home.data.mapper

import com.example.composeproject.core.BaseMapper
import com.example.composeproject.feature.home.data.model.SuggestedProductsResponse
import com.example.composeproject.feature.home.domain.model.HomeSuggestedProductsUiModel
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import javax.inject.Inject


class HomeSuggestedProductsMapper @Inject constructor() : BaseMapper() {

    fun map(response: SuggestedProductsResponse?): HomeSuggestedProductsUiModel {
        return HomeSuggestedProductsUiModel(
            suggestedProducts = ArrayList(response?.products?.map {
                SuggestedProductUiModel(
                    id = it.id.orEmpty(),
                    name = it.name.orEmpty(),
                    imageURL = it.imageURL.orEmpty(),
                    price = it.price ?: -1.0,
                    priceText = it.priceText.orEmpty(),
                    shortDescription = it.shortDescription.orEmpty(),
                    category = it.category.orEmpty(),
                    unitPrice = it.unitPrice ?: -1.0,
                    squareThumbnailURL = it.squareThumbnailURL.orEmpty(),
                    status = it.status ?: 0,
                )
            } ?: emptyList())
        )
    }
}