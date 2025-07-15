package com.example.composeproject.feature.basket.data.mapper

import com.example.composeproject.core.BaseMapper
import com.example.composeproject.feature.basket.data.db.BasketItemEntity
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import javax.inject.Inject

class BasketMapper @Inject constructor() : BaseMapper() {

    fun mapToEntity(uiModel: BasketItemUiModel): BasketItemEntity {
        return BasketItemEntity(
            id = uiModel.id,
            name = uiModel.name,
            imageURL = uiModel.imageURL,
            price = uiModel.price,
            priceText = uiModel.priceText,
            quantity = uiModel.quantity,
            totalPrice = uiModel.totalPrice
        )
    }

    fun mapToUiModel(entity: BasketItemEntity): BasketItemUiModel {
        return BasketItemUiModel(
            id = entity.id,
            name = entity.name,
            imageURL = entity.imageURL,
            price = entity.price,
            priceText = entity.priceText,
            quantity = entity.quantity,
            totalPrice = entity.totalPrice
        )
    }

    fun mapToEntityList(uiModels: List<BasketItemUiModel>): List<BasketItemEntity> {
        return uiModels.map { mapToEntity(it) }
    }

    fun mapToUiModelList(entities: List<BasketItemEntity>): List<BasketItemUiModel> {
        return entities.map { mapToUiModel(it) }
    }
} 