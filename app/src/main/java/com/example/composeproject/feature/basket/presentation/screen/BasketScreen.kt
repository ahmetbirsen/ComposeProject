package com.example.composeproject.feature.basket.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeproject.R
import com.example.composeproject.core.network.LoadingType
import com.example.composeproject.designsysytem.components.BasketBottomBar
import com.example.composeproject.designsysytem.components.BasketItemCard
import com.example.composeproject.designsysytem.components.BasketTopBar
import com.example.composeproject.designsysytem.components.CustomDialog
import com.example.composeproject.designsysytem.components.ProductCard
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.TitleLarge
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
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                BasketTopBar(
                    onNavigateBack = onNavigateBack,
                    onClearBasket = onClearBasket
                )
                
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(state.basketItems, key = { it.id }) { basketItem ->
                        BasketItemCard(
                            basketItem = basketItem,
                            onAdd = {
                                onAddToBasket(
                                    basketItem.id,
                                    basketItem.name,
                                    basketItem.imageURL,
                                    basketItem.price,
                                    basketItem.priceText
                                )
                            },
                            onRemove = {
                                onRemoveFromBasket(basketItem.id)
                            },
                            onProductClick = {
                                onNavigateToDetail(
                                    basketItem.id,
                                    basketItem.name,
                                    "Attribute", // Basket item'da attribute yok, placeholder
                                    basketItem.imageURL,
                                    basketItem.price,
                                    basketItem.priceText
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    if (state.basketItems.isNotEmpty()) {
                        item {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                color = Color(0xFFE0E0E0)
                            )
                        }
                    }
                    if (state.suggestedProducts.isNotEmpty()) {
                        item {
                            Text(
                                text = stringResource(R.string.suggested_products),
                                style = TitleLarge,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        }
                        item {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(state.suggestedProducts, key = { it.id }) { product ->
                                    ProductCard(
                                        name = product.name,
                                        attribute = product.shortDescription,
                                        priceText = product.priceText,
                                        imageUrl = product.squareThumbnailURL.ifEmpty { product.imageURL },
                                        quantity = 0,
                                        onAdd = {
                                            onAddToBasket(
                                                product.id,
                                                product.name,
                                                product.imageURL,
                                                product.price,
                                                product.priceText
                                            )
                                        },
                                        onRemove = { },
                                        onProductClick = {
                                            onNavigateToDetail(
                                                product.id,
                                                product.name,
                                                product.shortDescription,
                                                product.imageURL,
                                                product.price,
                                                product.priceText
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
                BasketBottomBar(
                    basketTotal = state.basketTotal,
                    onCompleteOrder = onCompleteOrder
                )
            }
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

