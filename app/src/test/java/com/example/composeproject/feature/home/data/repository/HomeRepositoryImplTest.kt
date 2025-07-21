package com.example.composeproject.feature.home.data.repository

import com.example.composeproject.core.model.RestResult
import com.example.composeproject.core.qualifiers.Dispatchers
import com.example.composeproject.feature.home.data.db.suggestedproducts.SuggestedProductsDao
import com.example.composeproject.feature.home.data.db.verticalproducts.VerticalProductsDao
import com.example.composeproject.feature.home.data.mapper.HomeMappers
import com.example.composeproject.feature.home.data.service.HomeService
import com.example.composeproject.feature.home.domain.model.HomeSuggestedProductsUiModel
import com.example.composeproject.feature.home.domain.model.HomeVerticalProductsUiModel
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel
import dagger.Lazy
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import javax.inject.Provider

@OptIn(ExperimentalCoroutinesApi::class)
class HomeRepositoryImplTest {

    private lateinit var homeRepository: HomeRepositoryImpl
    private lateinit var homeService: Lazy<HomeService>
    private lateinit var homeMappers: HomeMappers
    private lateinit var suggestedProductsDao: SuggestedProductsDao
    private lateinit var verticalProductsDao: VerticalProductsDao
    @Before
    fun setup() {
        homeService = mockk()
        homeMappers = mockk()
        suggestedProductsDao = mockk()
        verticalProductsDao = mockk()
        
        homeRepository = HomeRepositoryImpl(
            ioDispatchers = IO,
            homeService = homeService,
            homeMappers = homeMappers,
            suggestedProductsDao = suggestedProductsDao,
            verticalProductsDao = verticalProductsDao
        )
    }

    @Test
    fun `getVerticalProducts should return success with mapped data`() = runTest {
        // Given
        val mockResponse = mockk<com.example.composeproject.feature.home.data.model.VerticalProductsResponse>()
        val mockUiModel = HomeVerticalProductsUiModel(
            products = arrayListOf(
                VerticalProductsUiModel(
                    id = "1",
                    name = "Test Product",
                    imageURL = "test.jpg",
                    price = 100.0,
                    priceText = "100 TL"
                )
            )
        )
        
        coEvery { homeService.get().getProducts() } returns mockResponse
        coEvery { homeMappers.homeVerticalProductsMapper.map(mockResponse) } returns mockUiModel

        // When
        val result = homeRepository.getVerticalProducts()

        // Then
        assertTrue(result is RestResult.Success<*>)
        assertEquals(mockUiModel, (result as RestResult.Success<HomeVerticalProductsUiModel>).result)
    }

    @Test
    fun `getSuggestedProducts should return success with mapped data`() = runTest {
        // Given
        val mockResponse = mockk<com.example.composeproject.feature.home.data.model.SuggestedProductsResponse>()
        val mockUiModel = HomeSuggestedProductsUiModel(
            suggestedProducts = arrayListOf(
                SuggestedProductUiModel(
                    id = "1",
                    name = "Test Suggested Product",
                    imageURL = "test.jpg",
                    price = 50.0,
                    priceText = "50 TL"
                )
            )
        )
        
        coEvery { homeService.get().getSuggestedProducts() } returns mockResponse
        coEvery { homeMappers.homeSuggestedProductsMapper.map(mockResponse) } returns mockUiModel

        // When
        val result = homeRepository.getSuggestedProducts()

        // Then
        assertTrue(result is RestResult.Success<*>)
        assertEquals(mockUiModel, (result as RestResult.Success<HomeSuggestedProductsUiModel>).result)
    }

    @Test
    fun `saveSuggestedProductsToDatabase should call dao insert method`() = runTest {
        // Given
        val suggestedProducts = arrayListOf(
            SuggestedProductUiModel(
                id = "1",
                name = "Test Product",
                imageURL = "test.jpg",
                price = 100.0,
                priceText = "100 TL"
            )
        )
        val mockEntities = listOf(mockk<com.example.composeproject.feature.home.data.db.suggestedproducts.SuggestedProductEntity>())
        
        coEvery { homeMappers.suggestedProductDbMapper.mapToEntityList(suggestedProducts) } returns mockEntities
        coEvery { suggestedProductsDao.insertSuggestedProducts(mockEntities) } returns Unit

        // When
        homeRepository.saveSuggestedProductsToDatabase(suggestedProducts)

        // Then
        coVerify { suggestedProductsDao.insertSuggestedProducts(mockEntities) }
    }

    @Test
    fun `saveVerticalProductsToDatabase should call dao insert method`() = runTest {
        // Given
        val verticalProducts = arrayListOf(
            VerticalProductsUiModel(
                id = "1",
                name = "Test Product",
                imageURL = "test.jpg",
                price = 100.0,
                priceText = "100 TL"
            )
        )
        val mockEntities = listOf(mockk<com.example.composeproject.feature.home.data.db.verticalproducts.VerticalProductEntity>())
        
        coEvery { homeMappers.verticalProductDbMapper.mapToEntityList(verticalProducts) } returns mockEntities
        coEvery { verticalProductsDao.insertVerticalProducts(mockEntities) } returns Unit

        // When
        homeRepository.saveVerticalProductsToDatabase(verticalProducts)

        // Then
        coVerify { verticalProductsDao.insertVerticalProducts(mockEntities) }
    }
} 