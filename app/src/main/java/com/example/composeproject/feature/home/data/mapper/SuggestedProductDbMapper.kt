package com.example.composeproject.feature.home.data.mapper

import com.example.composeproject.core.BaseMapper
import com.example.composeproject.feature.home.data.db.suggestedproducts.SuggestedProductEntity
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import javax.inject.Inject

class SuggestedProductDbMapper @Inject constructor() : BaseMapper() {

    private fun mapToEntity(uiModel: SuggestedProductUiModel): SuggestedProductEntity {
        return SuggestedProductEntity(
            id = uiModel.id,
            name = uiModel.name,
            imageURL = uiModel.imageURL,
            price = uiModel.price,
            priceText = uiModel.priceText,
            shortDescription = uiModel.shortDescription,
            category = uiModel.category,
            unitPrice = uiModel.unitPrice,
            squareThumbnailURL = uiModel.squareThumbnailURL,
            status = uiModel.status
        )
    }

    private fun mapToUiModel(entity: SuggestedProductEntity): SuggestedProductUiModel {
        return SuggestedProductUiModel(
            id = entity.id,
            name = entity.name,
            imageURL = entity.imageURL,
            price = entity.price,
            priceText = entity.priceText,
            shortDescription = entity.shortDescription,
            category = entity.category,
            unitPrice = entity.unitPrice,
            squareThumbnailURL = entity.squareThumbnailURL,
            status = entity.status
        )
    }

    fun mapToEntityList(uiModels: List<SuggestedProductUiModel>): List<SuggestedProductEntity> {
        return uiModels.map { mapToEntity(it) }
    }

    fun mapToUiModelList(entities: List<SuggestedProductEntity>): List<SuggestedProductUiModel> {
        return entities.map { mapToUiModel(it) }
    }
} 