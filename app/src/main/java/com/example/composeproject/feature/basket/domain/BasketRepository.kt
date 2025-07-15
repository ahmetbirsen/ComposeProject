package com.example.composeproject.feature.basket.domain

import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BasketRepository {

    fun getAllBasketItems(): Flow<List<BasketItemUiModel>>

    suspend fun addToBasket(productId: String, name: String, imageURL: String, price: Double, priceText: String)

    suspend fun removeFromBasket(productId: String)

    suspend fun updateBasketItemQuantity(productId: String, quantity: Int)

    suspend fun clearBasket()

    fun getBasketTotal(): Flow<Double>

    fun getBasketItemCount(): Flow<Int>
} 