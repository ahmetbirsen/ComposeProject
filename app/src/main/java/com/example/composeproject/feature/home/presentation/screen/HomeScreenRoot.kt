package com.example.composeproject.feature.home.presentation.screen

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeproject.designsysytem.theme.Gray
import com.example.composeproject.feature.home.presentation.HomeViewModel


@Composable
fun HomeScreenRoute(
    onNavigateToDetail: (String, String, String, String, Double, String) -> Unit = { _, _, _, _, _, _ -> },
    onNavigateToBasket: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState()
    val isLoading by viewModel.networkLoadingStateFlow.collectAsState()
    val isLoadingBoolean = isLoading.isLoading

    Surface(color = Gray) {
        HomeScreen(
            state = state.value,
            isLoading = isLoadingBoolean,
            onRefresh = { viewModel.refreshData() },
            onAddToBasket = { productId, name, imageURL, price, priceText ->
                viewModel.addToBasket(productId, name, imageURL, price, priceText)
            },
            onRemoveFromBasket = { productId ->
                viewModel.removeFromBasket(productId)
            },
            onNavigateToDetail = onNavigateToDetail,
            onNavigateToBasket = onNavigateToBasket
        )
    }
}