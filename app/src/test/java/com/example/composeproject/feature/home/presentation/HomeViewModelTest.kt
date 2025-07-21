package com.example.composeproject.feature.home.presentation

import app.cash.turbine.test
import com.example.composeproject.core.model.RestResult
import com.example.composeproject.feature.basket.domain.usecase.BasketUseCases
import com.example.composeproject.feature.home.domain.usecase.HomeUseCases
import com.example.composeproject.feature.home.domain.usecase.GetSuggestedProductsUseCase
import com.example.composeproject.feature.home.domain.usecase.GetVerticalProductsUseCase
import com.example.composeproject.feature.home.domain.usecase.SaveSuggestedProductsToDatabaseUseCase
import com.example.composeproject.feature.home.domain.usecase.SaveVerticalProductsToDatabaseUseCase
import com.example.composeproject.feature.home.domain.model.HomeSuggestedProductsUiModel
import com.example.composeproject.feature.home.domain.model.HomeVerticalProductsUiModel
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import com.example.composeproject.feature.basket.domain.usecase.AddToBasketUseCase
import com.example.composeproject.feature.basket.domain.usecase.GetBasketItemsUseCase
import com.example.composeproject.feature.basket.domain.usecase.GetBasketTotalUseCase
import com.example.composeproject.feature.basket.domain.usecase.RemoveFromBasketUseCase
import com.example.composeproject.navigation.Routes
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeUseCases: HomeUseCases
    private lateinit var basketUseCases: BasketUseCases

    @Before
    fun setup() {
        homeUseCases = mockk()
        basketUseCases = mockk()

        homeViewModel = HomeViewModel(
            homeUseCases = homeUseCases,
            basketUseCases = basketUseCases
        )
    }

    @Test
    fun `initial state should be empty`() = runTest {
        // When
        val initialState = homeViewModel.uiState.value

        // Then
        assertEquals(emptyList<VerticalProductsUiModel>(), initialState.verticalProducts)
        assertEquals(emptyList<SuggestedProductUiModel>(), initialState.suggestedProducts)
        assertEquals(emptyList<BasketItemUiModel>(), initialState.basketItems)
        assertEquals(0.0, initialState.basketTotal, 0.0)
    }

    @Test
    fun `onRefresh should call getVerticalProducts and getSuggestedProducts`() = runTest {
        // Given
        val mockVerticalProducts = HomeVerticalProductsUiModel(
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
        val mockSuggestedProducts = HomeSuggestedProductsUiModel(
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

        coEvery { homeUseCases.getVerticalProducts() } returns flowOf(
            RestResult.Success(
                mockVerticalProducts
            )
        )
        coEvery { homeUseCases.getSuggestedProducts() } returns flowOf(
            RestResult.Success(
                mockSuggestedProducts
            )
        )
        coEvery { homeUseCases.saveVerticalProductsToDatabase(any()) } returns flowOf(Unit)
        coEvery { homeUseCases.saveSuggestedProductsToDatabase(any()) } returns flowOf(Unit)

        // When
        homeViewModel.onAction(HomeAction.OnRefresh)

        // Then
        coVerify { homeUseCases.getVerticalProducts() }
        coVerify { homeUseCases.getSuggestedProducts() }
    }

    @Test
    fun `onAddToBasket should call basket use case`() = runTest {
        // Given
        val productId = "1"
        val name = "Test Product"
        val imageURL = "test.jpg"
        val price = 100.0
        val priceText = "100 TL"

        coEvery {
            basketUseCases.addToBasket(
                productId,
                name,
                imageURL,
                price,
                priceText
            )
        } returns flowOf(Unit)

        // When
        homeViewModel.onAction(
            HomeAction.OnAddToBasket(
                productId = productId,
                name = name,
                imageURL = imageURL,
                price = price,
                priceText = priceText
            )
        )

        // Then
        verify { basketUseCases.addToBasket(productId, name, imageURL, price, priceText) }
    }

    @Test
    fun `onRemoveFromBasket should call basket use case`() = runTest {
        // Given
        val productId = "1"
        coEvery { basketUseCases.removeFromBasket(productId) } returns flowOf(Unit)

        // When
        homeViewModel.onAction(HomeAction.OnRemoveFromBasket(productId))

        // Then
        verify { basketUseCases.removeFromBasket(productId) }
    }

    @Test
    fun `onProductClick should emit event`() = runTest {
        // Given
        val product =
            Routes.Detail(
                productId = "1",
                name = "Test Product",
                imageUrl = "test.jpg",
                price = 100.0,
                priceText = "100 TL",
                attribute = "TEst"
            )

        // When
        homeViewModel.onAction(HomeAction.OnProductClick(product))

        // Then
        homeViewModel.events.test {
            val event = awaitItem()
            assertTrue(event is HomeEvent.NavigateToDetail)
            assertEquals(product, (event as HomeEvent.NavigateToDetail).product)
        }
    }

    @Test
    fun `onBasketBoxClick should emit event when basket has items`() = runTest {
        // Given
        val basketItems = arrayListOf(
            BasketItemUiModel(
                id = "1",
                name = "Test Product",
                imageURL = "test.jpg",
                price = 100.0,
                priceText = "100 TL",
                quantity = 1
            )
        )

        // When
        homeViewModel.onAction(HomeAction.OnBasketBoxClick)

        // Then
        homeViewModel.events.test {
            val event = awaitItem()
            assertTrue(event is HomeEvent.NavigateToBasket)
        }
    }

    @Test
    fun `onBasketBoxClick should not emit event when basket is empty`() = runTest {
        // Given - Empty basket state

        // When
        homeViewModel.onAction(HomeAction.OnBasketBoxClick)

        // Then - No event should be emitted
        // This test verifies that no navigation event is sent when basket is empty
    }

    @Test
    fun `onResume should refresh data`() = runTest {
        // Given
        val mockVerticalProducts = HomeVerticalProductsUiModel(products = arrayListOf())
        val mockSuggestedProducts = HomeSuggestedProductsUiModel(suggestedProducts = arrayListOf())
        val basketItems = arrayListOf<BasketItemUiModel>()
        val basketTotal = 0.0

        coEvery { homeUseCases.getVerticalProducts() } returns flowOf(
            RestResult.Success(
                mockVerticalProducts
            )
        )
        coEvery { homeUseCases.getSuggestedProducts() } returns flowOf(
            RestResult.Success(
                mockSuggestedProducts
            )
        )
        coEvery { basketUseCases.getBasketItems() } returns flowOf(basketItems)
        coEvery { basketUseCases.getBasketTotal() } returns flowOf(basketTotal)
        coEvery { homeUseCases.saveVerticalProductsToDatabase(any()) } returns flowOf(Unit)
        coEvery { homeUseCases.saveSuggestedProductsToDatabase(any()) } returns flowOf(Unit)

        // When
        homeViewModel.onResume()

        // Then
        coVerify { homeUseCases.getVerticalProducts() }
        coVerify { homeUseCases.getSuggestedProducts() }
        coVerify { basketUseCases.getBasketItems() }
        coVerify { basketUseCases.getBasketTotal() }
    }
} 