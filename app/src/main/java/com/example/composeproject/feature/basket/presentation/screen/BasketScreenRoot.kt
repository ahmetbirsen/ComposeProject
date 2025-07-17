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

@Composable
fun BasketScreenRoute(
    onNavigateBack: () -> Unit = {},
    onNavigateToDetail: (String, String, String, String, Double, String) -> Unit = { _, _, _, _, _, _ -> },
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
            }
        }
    }

    BasketScreen(
        state = state.value,
        onNavigateBack = onNavigateBack,
        onNavigateToDetail = onNavigateToDetail,
        onAddToBasket = { productId, name, imageUrl, price, priceText ->
            viewModel.handleAction(BasketAction.AddToBasket(productId, name, imageUrl, price, priceText))
        },
        onRemoveFromBasket = { productId ->
            viewModel.handleAction(BasketAction.RemoveFromBasket(productId))
        },
        onClearBasket = {
            viewModel.handleAction(BasketAction.ShowDialog(DialogType.CLEAR_BASKET))
        },
        onCompleteOrder = {
            viewModel.handleAction(BasketAction.ShowDialog(DialogType.COMPLETE_ORDER))
        },
        onDismissDialog = {
            viewModel.handleAction(BasketAction.HideDialog)
        },
        onConfirmDialog = {
            when (state.value.dialogType) {
                DialogType.CLEAR_BASKET -> viewModel.handleAction(BasketAction.ClearBasket)
                DialogType.COMPLETE_ORDER -> {
                    viewModel.handleAction(BasketAction.CompleteOrder)
                    onNavigateToHome()
                }
                null -> {}
            }
        }
    )
}