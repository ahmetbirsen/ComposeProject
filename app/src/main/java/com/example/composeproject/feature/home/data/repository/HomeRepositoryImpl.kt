package com.example.composeproject.feature.home.data.repository

import com.example.composeproject.core.BaseRepository
import com.example.composeproject.core.model.RestResult
import com.example.composeproject.core.model.mapOnSuccess
import com.example.composeproject.core.model.onError
import com.example.composeproject.core.qualifiers.Dispatchers
import com.example.composeproject.feature.home.data.mapper.HomeMappers
import com.example.composeproject.feature.home.data.service.HomeService
import com.example.composeproject.feature.home.domain.HomeRepository
import com.example.composeproject.feature.home.domain.model.HomeSuggestedProductsUiModel
import com.example.composeproject.feature.home.domain.model.HomeVerticalProductsUiModel
import dagger.Lazy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    @Dispatchers.IO private val ioDispatchers: CoroutineDispatcher,
    private val homeService: Lazy<HomeService>,
    private val homeMappers: HomeMappers
) : BaseRepository(), HomeRepository {
    override suspend fun getVerticalProducts(): RestResult<HomeVerticalProductsUiModel> =
        withContext(ioDispatchers) {
            request {
                homeService.get().getProducts()
            }.mapOnSuccess { responseList ->
                val firstResponse = responseList?.firstOrNull()
                homeMappers.homeVerticalProductsMapper.map(firstResponse)
            }
        }

    override suspend fun getSuggestedProducts(): RestResult<HomeSuggestedProductsUiModel> =
        withContext(ioDispatchers) {
            request {
                homeService.get().getSuggestedProducts()
            }.mapOnSuccess { responseList ->
                val firstResponse = responseList?.firstOrNull()
                homeMappers.homeSuggestedProductsMapper.map(firstResponse)
            }
        }
}