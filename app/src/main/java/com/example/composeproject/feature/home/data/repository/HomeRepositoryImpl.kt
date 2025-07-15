package com.example.composeproject.feature.home.data.repository

import com.example.composeproject.core.BaseRepository
import com.example.composeproject.core.model.RestResult
import com.example.composeproject.core.model.mapOnSuccess
import com.example.composeproject.core.model.onError
import com.example.composeproject.core.qualifiers.Dispatchers
import com.example.composeproject.feature.basket.data.db.BasketDao
import com.example.composeproject.feature.home.data.db.suggestedproducts.SuggestedProductsDao
import com.example.composeproject.feature.home.data.db.verticalproducts.VerticalProductsDao
import com.example.composeproject.feature.home.data.mapper.HomeMappers
import com.example.composeproject.feature.home.data.service.HomeService
import com.example.composeproject.feature.home.domain.HomeRepository
import com.example.composeproject.feature.home.domain.model.HomeSuggestedProductsUiModel
import com.example.composeproject.feature.home.domain.model.HomeVerticalProductsUiModel
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel
import dagger.Lazy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    @Dispatchers.IO private val ioDispatchers: CoroutineDispatcher,
    private val homeService: Lazy<HomeService>,
    private val homeMappers: HomeMappers,
    private val suggestedProductsDao: SuggestedProductsDao,
    private val verticalProductsDao: VerticalProductsDao,
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

    override suspend fun saveSuggestedProductsToDatabase(suggestedProducts: List<SuggestedProductUiModel>) =
        withContext(ioDispatchers) {
            val entities = homeMappers.suggestedProductDbMapper.mapToEntityList(suggestedProducts)
            suggestedProductsDao.insertSuggestedProducts(entities)
        }

    override fun getSuggestedProductsFromDatabase(): Flow<List<SuggestedProductUiModel>> =
        suggestedProductsDao.getAllSuggestedProducts().map { entities ->
            homeMappers.suggestedProductDbMapper.mapToUiModelList(entities)
        }

    override suspend fun saveVerticalProductsToDatabase(verticalProducts: List<VerticalProductsUiModel>) =
        withContext(ioDispatchers) {
            val entities = homeMappers.verticalProductDbMapper.mapToEntityList(verticalProducts)
            verticalProductsDao.insertVerticalProducts(entities)
        }

    override fun getVerticalProductsFromDatabase(): Flow<List<VerticalProductsUiModel>> =
        verticalProductsDao.getAllVerticalProducts().map { entities ->
            homeMappers.verticalProductDbMapper.mapToUiModelList(entities)
        }
}