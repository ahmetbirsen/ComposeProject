package com.example.composeproject.feature.basket.domain.usecase

import com.example.composeproject.core.qualifiers.Dispatchers
import com.example.composeproject.feature.basket.domain.BasketRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddToBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository,
    @Dispatchers.IO private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(
        productId: String,
        name: String,
        imageURL: String,
        price: Double,
        priceText: String
    ): Flow<Unit> = flow {
        basketRepository.addToBasket(productId, name, imageURL, price, priceText)
        emit(Unit)
    }.flowOn(ioDispatcher)
} 