package com.example.composeproject.feature.home.presentation.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeproject.R
import com.example.composeproject.core.ui.ObserveAsEvents
import com.example.composeproject.designsysytem.theme.Gray
import com.example.composeproject.feature.basket.presentation.BasketEvent
import com.example.composeproject.feature.home.presentation.HomeAction
import com.example.composeproject.feature.home.presentation.HomeEvent
import com.example.composeproject.feature.home.presentation.HomeViewModel
import com.example.composeproject.navigation.Routes


@Composable
fun HomeScreenRoute(
    onProductClick: (Routes.Detail) -> Unit = { _ -> },
    onBasketBoxClick: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState()
    val isLoading by viewModel.networkLoadingStateFlow.collectAsState()
    val isLoadingBoolean = isLoading.isLoading

    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            HomeEvent.Event -> TODO()
        }
    }

    HomeScreen(
        state = state.value,
        isLoading = isLoadingBoolean,
        onAction = { action ->
            when(action) {
                is HomeAction.OnBasketBoxClick -> onBasketBoxClick()
                is HomeAction.OnProductClick -> onProductClick(action.product)
                HomeAction.OnRefresh -> {
                    // Refresh action'ı ViewModel'e ilet
                }
                is HomeAction.OnAddToBasket -> {
                    // Add to basket action'ı ViewModel'e ilet
                }
                is HomeAction.OnRemoveFromBasket -> {
                    // Remove from basket action'ı ViewModel'e ilet
                }
            }
            viewModel.onAction(action)
        },
    )
}