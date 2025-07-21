package com.example.composeproject.feature.home.domain.usecase

import com.example.composeproject.core.model.RestResult
import com.example.composeproject.feature.home.domain.HomeRepository
import com.example.composeproject.feature.home.domain.model.HomeSuggestedProductsUiModel
import com.example.composeproject.feature.home.domain.model.HomeVerticalProductsUiModel
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeUseCasesTest {

    private lateinit var homeUseCases: HomeUseCases
    private lateinit var homeRepository: HomeRepository

    @Before
    fun setup() {
        homeRepository = mockk()
        
        homeUseCases = HomeUseCases(
            getVerticalProducts = GetVerticalProductsUseCase(homeRepository, kotlinx.coroutines.Dispatchers.IO),
            getSuggestedProducts = GetSuggestedProductsUseCase(homeRepository, kotlinx.coroutines.Dispatchers.IO),
            saveSuggestedProductsToDatabase = SaveSuggestedProductsToDatabaseUseCase(homeRepository, kotlinx.coroutines.Dispatchers.IO),
            saveVerticalProductsToDatabase = SaveVerticalProductsToDatabaseUseCase(homeRepository, kotlinx.coroutines.Dispatchers.IO)
        )
    }

    @Test
    fun `getVerticalProducts should return success result`() = runTest {
        // Given
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
        coEvery { homeRepository.getVerticalProducts() } returns RestResult.Success(mockUiModel)

        // When
        val result = homeUseCases.getVerticalProducts()

        // Then
        assertTrue(result is RestResult.Success<*>)
        assertEquals(mockUiModel, (result as RestResult.Success<HomeVerticalProductsUiModel>).result)
    }

    @Test
    fun `getSuggestedProducts should return success result`() = runTest {
        // Given
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
        coEvery { homeRepository.getSuggestedProducts() } returns RestResult.Success(mockUiModel)

        // When
        val result = homeUseCases.getSuggestedProducts()

        // Then
        assertTrue(result is RestResult.Success<*>)
        assertEquals(mockUiModel, (result as RestResult.Success<HomeSuggestedProductsUiModel>).result)
    }

    @Test
    fun `saveSuggestedProductsToDatabase should call repository method`() = runTest {
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
        coEvery { homeRepository.saveSuggestedProductsToDatabase(suggestedProducts) } returns Unit

        // When
        homeUseCases.saveSuggestedProductsToDatabase(suggestedProducts)

        // Then
        coVerify { homeRepository.saveSuggestedProductsToDatabase(suggestedProducts) }
    }

    @Test
    fun `saveVerticalProductsToDatabase should call repository method`() = runTest {
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
        coEvery { homeRepository.saveVerticalProductsToDatabase(verticalProducts) } returns Unit

        // When
        homeUseCases.saveVerticalProductsToDatabase(verticalProducts)

        // Then
        coVerify { homeRepository.saveVerticalProductsToDatabase(verticalProducts) }
    }
} 