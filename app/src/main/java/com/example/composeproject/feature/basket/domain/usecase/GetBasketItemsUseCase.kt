package com.example.composeproject.feature.basket.domain.usecase

import com.example.composeproject.core.qualifiers.Dispatchers
import com.example.composeproject.feature.basket.domain.BasketRepository
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBasketItemsUseCase @Inject constructor(
    private val basketRepository: BasketRepository,
    @Dispatchers.IO private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(): Flow<List<BasketItemUiModel>> =
        basketRepository.getAllBasketItems()
} 