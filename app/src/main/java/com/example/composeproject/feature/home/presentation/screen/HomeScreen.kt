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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.composeproject.R
import com.example.composeproject.designsysytem.components.AppTopBar
import com.example.composeproject.designsysytem.components.BasketTotalBox
import com.example.composeproject.designsysytem.sections.SuggestedProductsSection
import com.example.composeproject.designsysytem.sections.VerticalProductsSection
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.Gray
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
            onAction(HomeAction.OnRefresh)
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray)
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(modifier =
            Modifier
                .fillMaxSize()
        ) {
            item {
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
                Spacer(modifier = Modifier
                    .background(color = Gray)
                    .height(16.dp))
            }
            item {
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
                    },
                    isLoading = isLoading
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight()
                ) {
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
                        },
                        isLoading = isLoading
                    )
                }

            }
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