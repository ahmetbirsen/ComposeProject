package com.example.composeproject.designsysytem.contents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeproject.R
import com.example.composeproject.designsysytem.components.AppTopBar
import com.example.composeproject.designsysytem.components.BasketBottomBar
import com.example.composeproject.designsysytem.sections.SuggestedProductsSection
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.White
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import com.example.composeproject.feature.basket.presentation.BasketState
import com.example.composeproject.designsysytem.sections.BasketItemsSection
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel

@Composable
fun BasketContent(
    state: BasketState,
    onNavigateBack: () -> Unit,
    onAddToBasket: (String, String, String, Double, String) -> Unit,
    onRemoveFromBasket: (String) -> Unit,
    onNavigateToDetail: (String, String, String, String, Double, String) -> Unit,
    onClearBasket: () -> Unit,
    onCompleteOrder: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(
            leftIcon = {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onNavigateBack() },
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
                        .clickable { onClearBasket() },
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
                onAddToBasket = onAddToBasket,
                onRemoveFromBasket = onRemoveFromBasket,
                onNavigateToDetail = onNavigateToDetail
            )
            
            // Suggested Products Section (if basket is not empty)
            if (state.basketItems.isNotEmpty() && state.suggestedProducts.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                SuggestedProductsSection(
                    products = state.suggestedProducts,
                    basketItems = state.basketItems,
                    onAddToBasket = onAddToBasket,
                    onRemoveFromBasket = onRemoveFromBasket,
                    onNavigateToDetail = onNavigateToDetail
                )
            }
        }
        
        BasketBottomBar(
            basketTotal = state.basketTotal,
            onCompleteOrder = onCompleteOrder
        )
    }
}

@Preview(name = "Empty Basket Content")
@Composable
private fun BasketContentEmptyPreview() {
    ComposeProjectTheme {
        BasketContent(
            state = BasketState(),
            onNavigateBack = {},
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> },
            onClearBasket = {},
            onCompleteOrder = {}
        )
    }
}

@Preview(name = "Basket Content with Items")
@Composable
private fun BasketContentWithItemsPreview() {
    ComposeProjectTheme {
        BasketContent(
            state = BasketState(
                basketItems = listOf(
                    BasketItemUiModel(
                        id = "1",
                        name = "iPhone 15 Pro",
                        imageURL = "https://example.com/iphone.jpg",
                        attribute = "Apple iPhone 15 Pro 128GB",
                        price = 999.99,
                        priceText = "₺999,99",
                        quantity = 2,
                        totalPrice = 1999.98
                    ),
                    BasketItemUiModel(
                        id = "2",
                        name = "Samsung Galaxy S24",
                        imageURL = "https://example.com/samsung.jpg",
                        attribute = "Samsung Galaxy S24 256GB",
                        price = 899.99,
                        priceText = "₺899,99",
                        quantity = 1,
                        totalPrice = 899.99
                    )
                ),
                basketTotal = 2899.97
            ),
            onNavigateBack = {},
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> },
            onClearBasket = {},
            onCompleteOrder = {}
        )
    }
}

@Preview(name = "Basket Content with Items and Suggested Products")
@Composable
private fun BasketContentWithItemsAndSuggestedPreview() {
    ComposeProjectTheme {
        BasketContent(
            state = BasketState(
                basketItems = listOf(
                    BasketItemUiModel(
                        id = "1",
                        name = "iPhone 15 Pro",
                        imageURL = "https://example.com/iphone.jpg",
                        attribute = "Apple iPhone 15 Pro 128GB",
                        price = 999.99,
                        priceText = "₺999,99",
                        quantity = 1,
                        totalPrice = 999.99
                    )
                ),
                suggestedProducts = listOf(
                    SuggestedProductUiModel(
                        id = "s1",
                        name = "MacBook Pro M3",
                        imageURL = "https://example.com/macbook.jpg",
                        price = 2499.99,
                        priceText = "₺2.499,99",
                        shortDescription = "Apple MacBook Pro M3 14\"",
                        category = "Electronics",
                        unitPrice = 2499.99,
                        squareThumbnailURL = "https://example.com/macbook-thumb.jpg",
                        status = 1
                    ),
                    SuggestedProductUiModel(
                        id = "s2",
                        name = "iPad Pro 12.9",
                        imageURL = "https://example.com/ipad.jpg",
                        price = 1299.99,
                        priceText = "₺1.299,99",
                        shortDescription = "Apple iPad Pro 12.9\" M2",
                        category = "Electronics",
                        unitPrice = 1299.99,
                        squareThumbnailURL = "https://example.com/ipad-thumb.jpg",
                        status = 1
                    )
                ),
                basketTotal = 999.99
            ),
            onNavigateBack = {},
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> },
            onClearBasket = {},
            onCompleteOrder = {}
        )
    }
}

@Preview(name = "Basket Content with Many Items")
@Composable
private fun BasketContentWithManyItemsPreview() {
    ComposeProjectTheme {
        BasketContent(
            state = BasketState(
                basketItems = List(5) { index ->
                    BasketItemUiModel(
                        id = (index + 1).toString(),
                        name = "Ürün ${index + 1}",
                        imageURL = "https://example.com/product${index + 1}.jpg",
                        attribute = "Kategori ${index + 1}",
                        price = (100.0 + index * 50.0),
                        priceText = "₺${100 + index * 50},99",
                        quantity = (index % 3) + 1,
                        totalPrice = ((100.0 + index * 50.0) * ((index % 3) + 1))
                    )
                },
                suggestedProducts = listOf(
                    SuggestedProductUiModel(
                        id = "s1",
                        name = "Önerilen Ürün 1",
                        imageURL = "https://example.com/suggested1.jpg",
                        price = 299.99,
                        priceText = "₺299,99",
                        shortDescription = "Önerilen ürün açıklaması 1",
                        category = "Electronics",
                        unitPrice = 299.99,
                        squareThumbnailURL = "https://example.com/suggested1-thumb.jpg",
                        status = 1
                    )
                ),
                basketTotal = 1250.0
            ),
            onNavigateBack = {},
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> },
            onClearBasket = {},
            onCompleteOrder = {}
        )
    }
}

@Preview(name = "Basket Content with High Total")
@Composable
private fun BasketContentWithHighTotalPreview() {
    ComposeProjectTheme {
        BasketContent(
            state = BasketState(
                basketItems = listOf(
                    BasketItemUiModel(
                        id = "1",
                        name = "MacBook Pro M3",
                        imageURL = "https://example.com/macbook.jpg",
                        attribute = "Apple MacBook Pro M3 14\"",
                        price = 2499.99,
                        priceText = "₺2.499,99",
                        quantity = 3,
                        totalPrice = 7499.97
                    )
                ),
                basketTotal = 7499.97
            ),
            onNavigateBack = {},
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> },
            onClearBasket = {},
            onCompleteOrder = {}
        )
    }
} 