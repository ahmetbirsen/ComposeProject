package com.example.composeproject.feature.detail.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeproject.core.ui.ObserveAsEvents
import com.example.composeproject.feature.detail.DetailAction
import com.example.composeproject.feature.detail.DetailEvent
import com.example.composeproject.feature.detail.DetailViewModel
import com.example.composeproject.feature.home.presentation.HomeEvent

@Composable
fun DetailScreenRoute(
    onCloseClick: () -> Unit,
    onBasketBoxClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState()

    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            DetailEvent.Event -> TODO()
        }
    }

    DetailScreen(
        state = state.value,
        onAction = { action ->
            when (action) {
                DetailAction.OnCloseClick -> onCloseClick()
                DetailAction.OnBasketBoxClick -> onBasketBoxClick()
                else -> Unit
            }
            viewModel.handleAction(action)
        }
    )
}