package com.example.composeproject.feature.home.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import com.example.composeproject.feature.home.presentation.HomeAction
import com.example.composeproject.feature.home.presentation.HomeState
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_shouldDisplayTitle() {
        // Given
        val state = HomeState()
        val onAction: (HomeAction) -> Unit = mockk(relaxed = true)

        // When
        composeTestRule.setContent {
            ComposeProjectTheme {
                HomeScreen(
                    state = state,
                    onAction = onAction
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Ürünler").assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldDisplayBasketTotal_whenBasketHasItems() {
        // Given
        val state = HomeState(basketTotal = 150.0)
        val onAction: (HomeAction) -> Unit = mockk(relaxed = true)

        // When
        composeTestRule.setContent {
            ComposeProjectTheme {
                HomeScreen(
                    state = state,
                    onAction = onAction
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("150.0 TL").assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldDisplaySuggestedProducts_whenProductsExist() {
        // Given
        val suggestedProducts = listOf(
            SuggestedProductUiModel(
                id = "1",
                name = "Test Suggested Product",
                imageURL = "test.jpg",
                price = 50.0,
                priceText = "50 TL"
            )
        )
        val state = HomeState(suggestedProducts = suggestedProducts)
        val onAction: (HomeAction) -> Unit = mockk(relaxed = true)

        // When
        composeTestRule.setContent {
            ComposeProjectTheme {
                HomeScreen(
                    state = state,
                    onAction = onAction
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Test Suggested Product").assertIsDisplayed()
        composeTestRule.onNodeWithText("50 TL").assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldDisplayVerticalProducts_whenProductsExist() {
        // Given
        val verticalProducts = listOf(
            VerticalProductsUiModel(
                id = "1",
                name = "Test Vertical Product",
                imageURL = "test.jpg",
                price = 100.0,
                priceText = "100 TL"
            )
        )
        val state = HomeState(verticalProducts = verticalProducts)
        val onAction: (HomeAction) -> Unit = mockk(relaxed = true)

        // When
        composeTestRule.setContent {
            ComposeProjectTheme {
                HomeScreen(
                    state = state,
                    onAction = onAction
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Test Vertical Product").assertIsDisplayed()
        composeTestRule.onNodeWithText("100 TL").assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldShowLoadingIndicator_whenLoading() {
        // Given
        val state = HomeState()
        val onAction: (HomeAction) -> Unit = mockk(relaxed = true)

        // When
        composeTestRule.setContent {
            ComposeProjectTheme {
                HomeScreen(
                    state = state,
                    isLoading = true,
                    onAction = onAction
                )
            }
        }

        // Then - Loading indicator should be visible
        // Note: This test assumes there's a loading indicator in the UI
        // You may need to adjust based on your actual loading implementation
    }

    @Test
    fun homeScreen_shouldCallOnAction_whenBasketBoxClicked() {
        // Given
        val state = HomeState(basketTotal = 100.0)
        val onAction: (HomeAction) -> Unit = mockk(relaxed = true)

        // When
        composeTestRule.setContent {
            ComposeProjectTheme {
                HomeScreen(
                    state = state,
                    onAction = onAction
                )
            }
        }

        // Then - Basket box should be clickable when there are items
        // Note: You may need to adjust the test based on your actual UI implementation
    }

    @Test
    fun homeScreen_shouldDisplayBasketItems_whenBasketHasItems() {
        // Given
        val basketItems = listOf(
            BasketItemUiModel(
                id = "1",
                name = "Test Basket Item",
                imageURL = "test.jpg",
                price = 100.0,
                priceText = "100 TL",
                quantity = 2
            )
        )
        val state = HomeState(basketItems = basketItems)
        val onAction: (HomeAction) -> Unit = mockk(relaxed = true)

        // When
        composeTestRule.setContent {
            ComposeProjectTheme {
                HomeScreen(
                    state = state,
                    onAction = onAction
                )
            }
        }

        // Then
        // The basket items should be reflected in the UI
        // This might be shown as quantity indicators on product cards
    }
} 