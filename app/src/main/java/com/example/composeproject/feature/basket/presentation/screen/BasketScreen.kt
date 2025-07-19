package com.example.composeproject.feature.basket.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeproject.R
import com.example.composeproject.core.network.LoadingType
import com.example.composeproject.designsysytem.components.AppTopBar
import com.example.composeproject.designsysytem.components.BasketBottomBar
import com.example.composeproject.designsysytem.components.CustomDialog
import com.example.composeproject.designsysytem.components.DialogType
import com.example.composeproject.designsysytem.sections.BasketItemsSection
import com.example.composeproject.designsysytem.sections.SuggestedProductsSection
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.White
import com.example.composeproject.feature.basket.presentation.BasketAction
import com.example.composeproject.feature.basket.presentation.BasketState
import com.example.composeproject.feature.basket.presentation.BasketViewModel
import com.example.composeproject.feature.home.presentation.HomeAction
import com.example.composeproject.navigation.Routes

@Composable
fun BasketScreen(
    state: BasketState,
    onAction: (BasketAction) -> Unit,
    viewModel: BasketViewModel = hiltViewModel()
) {
    val loadingState = viewModel.networkLoadingStateFlow.collectAsState()
    val isButtonLoading =
        loadingState.value.isLoading && loadingState.value.loadingType == LoadingType.Button

    Surface(color = Color.White) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AppTopBar(
                    leftIcon = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onAction(BasketAction.OnCloseClick) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_close),
                                contentDescription = stringResource(R.string.back),
                                tint = White,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    },
                    rightComponent = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    onAction(BasketAction.ShowDialog(DialogType.CLEAR_BASKET))
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_remove),
                                contentDescription = stringResource(R.string.clear_basket),
                                tint = White,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    },
                    title = stringResource(R.string.my_basket)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    // Basket Items Section
                    BasketItemsSection(
                        basketItems = state.basketItems,
                        onAddToBasket = { productId, name, imageURL, price, priceText ->
                            onAction(
                                BasketAction.OnAddToBasket(
                                    productId = productId,
                                    productName = name,
                                    productImageUrl = imageURL,
                                    productPrice = price,
                                    productPriceText = priceText
                                )
                            )
                        },
                        onRemoveFromBasket = { id ->
                            onAction(BasketAction.RemoveFromBasket(id))
                        },
                        omProductClick = { product ->
                            onAction(BasketAction.OnProductClick(product))
                        }
                    )

                    // Suggested Products Section (if basket is not empty)
                    if (state.basketItems.isNotEmpty() && state.suggestedProducts.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        SuggestedProductsSection(
                            products = state.suggestedProducts,
                            basketItems = state.basketItems,
                            onAddToBasket = { productId, name, imageURL, price, priceText ->
                                onAction(
                                    BasketAction.OnAddToBasket(
                                        productId = productId,
                                        productName = name,
                                        productImageUrl = imageURL,
                                        productPrice = price,
                                        productPriceText = priceText
                                    )
                                )
                            },
                            onRemoveFromBasket = { id ->
                                onAction(BasketAction.RemoveFromBasket(id))
                            },
                            onProductClick = { product ->
                                onAction(BasketAction.OnProductClick(product))
                            }
                        )
                    }
                }

                BasketBottomBar(
                    basketTotal = state.basketTotal,
                    onCompleteOrder = {
                        onAction(BasketAction.ShowDialog(DialogType.COMPLETE_ORDER))
                    }
                )
            }

            if (state.showDialog && state.dialogType != null) {
                CustomDialog(
                    dialogType = state.dialogType,
                    onDismiss = {
                        onAction(BasketAction.HideDialog)
                    },
                    onConfirm = {
                        when (state.dialogType) {
                            DialogType.CLEAR_BASKET -> {
                                onAction(BasketAction.ClearBasket)
                            }

                            DialogType.COMPLETE_ORDER -> {
                                onAction(BasketAction.CompleteOrder)
                            }

                            DialogType.ERROR -> {

                            }
                        }
                    },
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
            state = BasketState(),
            onAction = {}
        )
    }
}

