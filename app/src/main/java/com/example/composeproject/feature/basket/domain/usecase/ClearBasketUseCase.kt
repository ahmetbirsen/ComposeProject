package com.example.composeproject.feature.basket.domain.usecase

import com.example.composeproject.core.qualifiers.Dispatchers
import com.example.composeproject.feature.basket.domain.BasketRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ClearBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository,
    @Dispatchers.IO private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() {
        basketRepository.clearBasket()
    }
} 