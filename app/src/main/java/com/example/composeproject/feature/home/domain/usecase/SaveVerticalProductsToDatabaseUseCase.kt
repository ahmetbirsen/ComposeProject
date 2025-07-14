package com.example.composeproject.feature.home.domain.usecase

import com.example.composeproject.core.qualifiers.Dispatchers
import com.example.composeproject.feature.home.domain.HomeRepository
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SaveVerticalProductsToDatabaseUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    @Dispatchers.IO private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(verticalProducts: List<VerticalProductsUiModel>): Flow<Unit> = flow {
        homeRepository.saveVerticalProductsToDatabase(verticalProducts)
        emit(Unit)
    }.flowOn(ioDispatcher)
} 