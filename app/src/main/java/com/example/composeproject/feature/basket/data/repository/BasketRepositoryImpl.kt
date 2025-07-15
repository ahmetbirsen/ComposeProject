package com.example.composeproject.feature.basket.data.repository

import com.example.composeproject.core.qualifiers.Dispatchers
import com.example.composeproject.feature.basket.data.db.BasketDao
import com.example.composeproject.feature.basket.data.db.BasketItemEntity
import com.example.composeproject.feature.basket.data.mapper.BasketMapper
import com.example.composeproject.feature.basket.domain.BasketRepository
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    @Dispatchers.IO private val ioDispatcher: CoroutineDispatcher,
    private val basketDao: BasketDao,
    private val basketMapper: BasketMapper
) : BasketRepository {

    override fun getAllBasketItems(): Flow<List<BasketItemUiModel>> =
        basketDao.getAllBasketItems().map { entities ->
            basketMapper.mapToUiModelList(entities)
        }

    override suspend fun addToBasket(
        productId: String,
        name: String,
        imageURL: String,
        price: Double,
        priceText: String
    ) = withContext(ioDispatcher) {
        val existingItem = basketDao.getBasketItemById(productId)
        
        if (existingItem != null) {
            // Mevcut item varsa quantity'yi artır
            val updatedItem = existingItem.copy(
                quantity = existingItem.quantity + 1,
                totalPrice = (existingItem.quantity + 1) * existingItem.price
            )
            basketDao.updateBasketItem(updatedItem)
        } else {
            // Yeni ürün ekle
            val newItem = BasketItemEntity(
                id = productId,
                name = name,
                imageURL = imageURL,
                price = price,
                priceText = priceText,
                quantity = 1,
                totalPrice = price
            )
            basketDao.insertBasketItem(newItem)
        }
    }

    override suspend fun removeFromBasket(productId: String) = withContext(ioDispatcher) {
        val existingItem = basketDao.getBasketItemById(productId)
        
        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                // Quantity'yi azalt
                val updatedItem = existingItem.copy(
                    quantity = existingItem.quantity - 1,
                    totalPrice = (existingItem.quantity - 1) * existingItem.price
                )
                basketDao.updateBasketItem(updatedItem)
            } else {
                // Son ürünse tamamen kaldır
                basketDao.removeBasketItem(productId)
            }
        }
    }

    override suspend fun updateBasketItemQuantity(productId: String, quantity: Int) = withContext(ioDispatcher) {
        val existingItem = basketDao.getBasketItemById(productId)
        
        if (existingItem != null) {
            if (quantity > 0) {
                val updatedItem = existingItem.copy(
                    quantity = quantity,
                    totalPrice = quantity * existingItem.price
                )
                basketDao.updateBasketItem(updatedItem)
            } else {
                basketDao.removeBasketItem(productId)
            }
        }
    }

    override suspend fun clearBasket() = withContext(ioDispatcher) {
        basketDao.clearBasket()
    }

    override fun getBasketTotal(): Flow<Double> =
        basketDao.getBasketTotal().map { total ->
            total ?: 0.0
        }

    override fun getBasketItemCount(): Flow<Int> =
        basketDao.getBasketItemCount()
} 