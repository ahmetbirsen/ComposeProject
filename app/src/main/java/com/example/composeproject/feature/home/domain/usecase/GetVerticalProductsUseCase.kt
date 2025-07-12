package com.example.composeproject.feature.home.domain.usecase

import com.example.composeproject.core.extension.buildDefaultFlow
import com.example.composeproject.core.qualifiers.Dispatchers
import com.example.composeproject.feature.home.domain.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetVerticalProductsUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    @Dispatchers.IO private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke() = flow {
        emit(homeRepository.getVerticalProducts())
    }.buildDefaultFlow(ioDispatcher)
}