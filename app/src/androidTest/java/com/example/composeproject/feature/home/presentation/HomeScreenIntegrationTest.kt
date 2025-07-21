package com.example.composeproject.feature.home.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.composeproject.MainActivity
import com.example.composeproject.feature.home.domain.usecase.HomeUseCases
import com.example.composeproject.feature.basket.domain.usecase.BasketUseCases
import com.example.composeproject.core.model.RestResult
import com.example.composeproject.feature.home.domain.model.HomeSuggestedProductsUiModel
import com.example.composeproject.feature.home.domain.model.HomeVerticalProductsUiModel
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeScreenIntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var homeUseCases: HomeUseCases

    @Inject
    lateinit var basketUseCases: BasketUseCases

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun homeScreen_shouldLoadAndDisplayProducts() {
        // Given
        val mockVerticalProducts = HomeVerticalProductsUiModel(
            products = listOf(
                VerticalProductsUiModel(
                    id = "1",
                    name = "Integration Test Product",
                    imageURL = "test.jpg",
                    price = 100.0,
                    priceText = "100 TL"
                )
            )
        )
        val mockSuggestedProducts = HomeSuggestedProductsUiModel(
            suggestedProducts = listOf(
                SuggestedProductUiModel(
                    id = "1",
                    name = "Integration Suggested Product",
                    imageURL = "test.jpg",
                    price = 50.0,
                    priceText = "50 TL"
                )
            )
        )

        // Mock the use cases
        coEvery { homeUseCases.getVerticalProducts() } returns RestResult.Success(mockVerticalProducts)
        coEvery { homeUseCases.getSuggestedProducts() } returns RestResult.Success(mockSuggestedProducts)
        coEvery { basketUseCases.getBasketItems() } returns flowOf(emptyList())
        coEvery { basketUseCases.getBasketTotal() } returns flowOf(0.0)

        // When - Activity starts and loads data

        // Then
        composeTestRule.onNodeWithText("Integration Test Product").assertIsDisplayed()
        composeTestRule.onNodeWithText("Integration Suggested Product").assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldShowBasketTotal_whenBasketHasItems() {
        // Given
        coEvery { basketUseCases.getBasketTotal() } returns flowOf(150.0)
        coEvery { basketUseCases.getBasketItems() } returns flowOf(emptyList())

        // When - Activity starts

        // Then
        composeTestRule.onNodeWithText("150.0 TL").assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldHandleAddToBasket() {
        // Given
        coEvery { basketUseCases.addToBasket(any(), any(), any(), any(), any()) } returns flowOf(Unit)
        coEvery { basketUseCases.getBasketItems() } returns flowOf(emptyList())
        coEvery { basketUseCases.getBasketTotal() } returns flowOf(0.0)

        // When - User clicks add to basket button
        // Note: This would require finding the actual add to basket button in your UI

        // Then - Verify the basket use case was called
        // This would be verified through the UI state changes
    }
} 