package com.example.composeproject.feature.basket.presentation.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeproject.R
import com.example.composeproject.core.ui.ObserveAsEvents
import com.example.composeproject.designsysytem.components.DialogType
import com.example.composeproject.feature.basket.presentation.BasketAction
import com.example.composeproject.feature.basket.presentation.BasketEvent
import com.example.composeproject.feature.basket.presentation.BasketViewModel
import com.example.composeproject.navigation.Routes

@Composable
fun BasketScreenRoute(
    onProductClick: (Routes.Detail) -> Unit = { _ -> },
    onCloseClick: () -> Unit,
    onNavigateToHome: () -> Unit = {},
    viewModel: BasketViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.uiState.collectAsState()

    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is BasketEvent.BasketCleared -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.basket_cleared),
                    Toast.LENGTH_SHORT
                ).show()
                onNavigateToHome()
            }
            is BasketEvent.ShowError -> {
                Toast.makeText(
                    context,
                    event.message.asString(context),
                    Toast.LENGTH_SHORT
                ).show()
            }
            BasketEvent.OrderCompleted -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.order_completed),
                    Toast.LENGTH_SHORT
                ).show()
                onNavigateToHome()
            }
        }
    }

    BasketScreen(
        state = state.value,
        onAction = { action ->
            when (action) {
                is BasketAction.OnProductClick -> onProductClick(action.product)
                is BasketAction.OnCloseClick -> onCloseClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}