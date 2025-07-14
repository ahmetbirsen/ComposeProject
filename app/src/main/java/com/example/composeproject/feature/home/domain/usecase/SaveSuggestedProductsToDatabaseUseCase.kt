package com.example.composeproject.feature.home.domain.usecase

import com.example.composeproject.core.qualifiers.Dispatchers
import com.example.composeproject.feature.home.domain.HomeRepository
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SaveSuggestedProductsToDatabaseUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    @Dispatchers.IO private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(suggestedProducts: List<SuggestedProductUiModel>): Flow<Unit> = flow {
        homeRepository.saveSuggestedProductsToDatabase(suggestedProducts)
        emit(Unit)
    }.flowOn(ioDispatcher)
} 