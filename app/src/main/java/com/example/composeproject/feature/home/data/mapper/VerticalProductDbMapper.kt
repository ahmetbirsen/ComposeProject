package com.example.composeproject.feature.home.data.mapper

import com.example.composeproject.core.BaseMapper
import com.example.composeproject.feature.home.data.db.verticalproducts.VerticalProductEntity
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel
import javax.inject.Inject

class VerticalProductDbMapper @Inject constructor() : BaseMapper() {

    private fun mapToEntity(uiModel: VerticalProductsUiModel): VerticalProductEntity {
        return VerticalProductEntity(
            id = uiModel.id,
            name = uiModel.name,
            attribute = uiModel.attribute,
            imageURL = uiModel.imageURL,
            price = uiModel.price,
            priceText = uiModel.priceText
        )
    }

    private fun mapToUiModel(entity: VerticalProductEntity): VerticalProductsUiModel {
        return VerticalProductsUiModel(
            id = entity.id,
            name = entity.name,
            attribute = entity.attribute,
            imageURL = entity.imageURL,
            price = entity.price,
            priceText = entity.priceText
        )
    }

    fun mapToEntityList(uiModels: List<VerticalProductsUiModel>): List<VerticalProductEntity> {
        return uiModels.map { mapToEntity(it) }
    }

    fun mapToUiModelList(entities: List<VerticalProductEntity>): List<VerticalProductsUiModel> {
        return entities.map { mapToUiModel(it) }
    }
} 