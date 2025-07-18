package com.example.composeproject.feature.basket.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeproject.core.network.LoadingType
import com.example.composeproject.designsysytem.components.CustomDialog
import com.example.composeproject.designsysytem.contents.BasketContent
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.feature.basket.presentation.BasketState
import com.example.composeproject.feature.basket.presentation.BasketViewModel

@Composable
fun BasketScreen(
    state: BasketState,
    onNavigateBack: () -> Unit = {},
    onNavigateToDetail: (String, String, String, String, Double, String) -> Unit = { _, _, _, _, _, _ -> },
    onAddToBasket: (String, String, String, Double, String) -> Unit = { _, _, _, _, _ -> },
    onRemoveFromBasket: (String) -> Unit = { _ -> },
    onClearBasket: () -> Unit = {},
    onCompleteOrder: () -> Unit = {},
    onDismissDialog: () -> Unit = {},
    onConfirmDialog: () -> Unit = {},
    viewModel: BasketViewModel = hiltViewModel()
) {
    val loadingState = viewModel.networkLoadingStateFlow.collectAsState()
    val isButtonLoading = loadingState.value.isLoading && loadingState.value.loadingType == LoadingType.Button
    
    Surface(color = Color.White) {
        Box(modifier = Modifier.fillMaxSize()) {
            BasketContent(
                state = state,
                onNavigateBack = onNavigateBack,
                onAddToBasket = onAddToBasket,
                onRemoveFromBasket = onRemoveFromBasket,
                onNavigateToDetail = onNavigateToDetail,
                onClearBasket = onClearBasket,
                onCompleteOrder = onCompleteOrder
            )
            
            if (state.showDialog && state.dialogType != null) {
                CustomDialog(
                    dialogType = state.dialogType,
                    onDismiss = onDismissDialog,
                    onConfirm = onConfirmDialog,
                    isLoading = isButtonLoading
                )
            }
        }
    }
}

@Preview
@Composable
private fun BasketScreenPreview() {
    ComposeProjectTheme {
        BasketScreen(
            state = BasketState()
        )
    }
}

