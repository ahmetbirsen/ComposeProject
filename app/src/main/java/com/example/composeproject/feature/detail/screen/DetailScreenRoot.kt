package com.example.composeproject.feature.detail.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeproject.feature.detail.DetailAction
import com.example.composeproject.feature.detail.DetailViewModel

@Composable
fun DetailScreenRoute(
    onNavigateBack: () -> Unit,
    onNavigateToBasket: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState()

    DetailScreen(
        state = state.value,
        onNavigateBack = onNavigateBack,
        onAddToBasket = { id, name, imageUrl, price, priceText ->
            viewModel.handleAction(DetailAction.AddToBasket(id, name, imageUrl, price, priceText))
        },
        onRemoveFromBasket = { id ->
            viewModel.handleAction(DetailAction.RemoveFromBasket(id))
        },
        onNavigateToBasket = onNavigateToBasket
    )
}