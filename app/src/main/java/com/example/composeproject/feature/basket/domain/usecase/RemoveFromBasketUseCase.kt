package com.example.composeproject.feature.basket.domain.usecase

import com.example.composeproject.core.qualifiers.Dispatchers
import com.example.composeproject.feature.basket.domain.BasketRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoveFromBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository,
    @Dispatchers.IO private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(productId: String): Flow<Unit> = flow {
        basketRepository.removeFromBasket(productId)
        emit(Unit)
    }.flowOn(ioDispatcher)
} 