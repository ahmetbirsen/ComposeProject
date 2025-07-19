package com.example.composeproject.feature.home.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.util.Log
import com.example.composeproject.R
import com.example.composeproject.designsysytem.components.AppTopBar
import com.example.composeproject.designsysytem.components.BasketTotalBox
import com.example.composeproject.designsysytem.sections.SuggestedProductsSection
import com.example.composeproject.designsysytem.sections.VerticalProductsSection
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.feature.home.presentation.HomeAction
import com.example.composeproject.feature.home.presentation.HomeState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    state: HomeState,
    isLoading: Boolean = false,
    onAction: (HomeAction) -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = {
            Log.d("HomeScreen", "Pull to refresh triggered")
            onAction(HomeAction.OnRefresh)
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppTopBar(
                rightComponent = {
                    BasketTotalBox(
                        basketTotal = state.basketTotal,
                        modifier = Modifier
                            .width(100.dp)
                            .height(34.dp)
                            .clickable {
                                if (state.basketTotal > 0) {
                                    onAction(HomeAction.OnBasketBoxClick)
                                }
                            }
                    )
                },
                title = stringResource(R.string.home_products_title)
            )
            Spacer(modifier = Modifier.height(8.dp))
            SuggestedProductsSection(
                products = state.suggestedProducts,
                basketItems = state.basketItems,
                onAddToBasket = { productId, name, imageURL, price, priceText ->
                    onAction(
                        HomeAction.OnAddToBasket(
                            productId = productId,
                            name = name,
                            imageURL = imageURL,
                            price = price,
                            priceText = priceText
                        )
                    )
                },
                onRemoveFromBasket = { id ->
                    onAction(HomeAction.OnRemoveFromBasket(id))
                },
                onProductClick = { product ->
                    onAction(HomeAction.OnProductClick(product))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            VerticalProductsSection(
                products = state.verticalProducts,
                basketItems = state.basketItems,
                onAddToBasket = { productId, name, imageURL, price, priceText ->
                    onAction(
                        HomeAction.OnAddToBasket(
                            productId = productId,
                            name = name,
                            imageURL = imageURL,
                            price = price,
                            priceText = priceText
                        )
                    )
                },
                onRemoveFromBasket = { id ->
                    onAction(HomeAction.OnRemoveFromBasket(id))
                },
                onProductClick = { product ->
                    onAction(HomeAction.OnProductClick(product))
                }
            )
        }
        
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ComposeProjectTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}