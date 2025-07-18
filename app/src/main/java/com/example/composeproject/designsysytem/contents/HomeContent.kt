package com.example.composeproject.designsysytem.contents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeproject.R
import com.example.composeproject.designsysytem.components.AppTopBar
import com.example.composeproject.designsysytem.components.BasketTotalBox
import com.example.composeproject.designsysytem.sections.SuggestedProductsSection
import com.example.composeproject.designsysytem.sections.VerticalProductsSection
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel
import com.example.composeproject.feature.home.presentation.HomeState

@Composable
fun HomeContent(
    state: HomeState,
    onAddToBasket: (String, String, String, Double, String) -> Unit,
    onRemoveFromBasket: (String) -> Unit,
    onNavigateToDetail: (String, String, String, String, Double, String) -> Unit,
    onNavigateToBasket: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(
            rightComponent = {
                BasketTotalBox(
                    basketTotal = state.basketTotal,
                    modifier = Modifier
                        .width(100.dp)
                        .height(34.dp)
                        .clickable { onNavigateToBasket() }
                )
            },
            title = stringResource(R.string.home_products_title)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        SuggestedProductsSection(
            products = state.suggestedProducts,
            basketItems = state.basketItems,
            onAddToBasket = onAddToBasket,
            onRemoveFromBasket = onRemoveFromBasket,
            onNavigateToDetail = onNavigateToDetail
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        VerticalProductsSection(
            products = state.verticalProducts,
            basketItems = state.basketItems,
            onAddToBasket = onAddToBasket,
            onRemoveFromBasket = onRemoveFromBasket,
            onNavigateToDetail = onNavigateToDetail
        )
    }
}

@Preview(name = "Empty Home Content")
@Composable
private fun HomeContentEmptyPreview() {
    ComposeProjectTheme {
        HomeContent(
            state = HomeState(),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> },
            onNavigateToBasket = {}
        )
    }
}

@Preview(name = "Home Content with Suggested Products")
@Composable
private fun HomeContentWithSuggestedProductsPreview() {
    ComposeProjectTheme {
        HomeContent(
            state = HomeState(
                suggestedProducts = listOf(
                    SuggestedProductUiModel(
                        id = "1",
                        name = "iPhone 15 Pro",
                        imageURL = "https://example.com/iphone.jpg",
                        price = 999.99,
                        priceText = "₺999,99",
                        shortDescription = "Apple iPhone 15 Pro 128GB",
                        category = "Electronics",
                        unitPrice = 999.99,
                        squareThumbnailURL = "https://example.com/iphone-thumb.jpg",
                        status = 1
                    ),
                    SuggestedProductUiModel(
                        id = "2",
                        name = "Samsung Galaxy S24",
                        imageURL = "https://example.com/samsung.jpg",
                        price = 899.99,
                        priceText = "₺899,99",
                        shortDescription = "Samsung Galaxy S24 256GB",
                        category = "Electronics",
                        unitPrice = 899.99,
                        squareThumbnailURL = "https://example.com/samsung-thumb.jpg",
                        status = 1
                    )
                ),
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
                basketTotal = 999.99
            ),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> },
            onNavigateToBasket = {}
        )
    }
}

@Preview(name = "Home Content with Vertical Products")
@Composable
private fun HomeContentWithVerticalProductsPreview() {
    ComposeProjectTheme {
        HomeContent(
            state = HomeState(
                verticalProducts = listOf(
                    VerticalProductsUiModel(
                        id = "1",
                        name = "Nike Air Max",
                        attribute = "Spor Ayakkabı",
                        imageURL = "https://example.com/nike.jpg",
                        price = 129.99,
                        priceText = "₺129,99"
                    ),
                    VerticalProductsUiModel(
                        id = "2",
                        name = "Adidas Ultraboost",
                        attribute = "Koşu Ayakkabısı",
                        imageURL = "https://example.com/adidas.jpg",
                        price = 159.99,
                        priceText = "₺159,99"
                    ),
                    VerticalProductsUiModel(
                        id = "3",
                        name = "Puma RS-X",
                        attribute = "Retro Sneaker",
                        imageURL = "https://example.com/puma.jpg",
                        price = 89.99,
                        priceText = "₺89,99"
                    )
                ),
                basketItems = listOf(
                    BasketItemUiModel(
                        id = "1",
                        name = "Nike Air Max",
                        imageURL = "https://example.com/nike.jpg",
                        attribute = "Spor Ayakkabı",
                        price = 129.99,
                        priceText = "₺129,99",
                        quantity = 2,
                        totalPrice = 259.98
                    )
                ),
                basketTotal = 259.98
            ),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> },
            onNavigateToBasket = {}
        )
    }
}

@Preview(name = "Full Home Content")
@Composable
private fun HomeContentFullPreview() {
    ComposeProjectTheme {
        HomeContent(
            state = HomeState(
                suggestedProducts = listOf(
                    SuggestedProductUiModel(
                        id = "s1",
                        name = "iPhone 15 Pro",
                        imageURL = "https://example.com/iphone.jpg",
                        price = 999.99,
                        priceText = "₺999,99",
                        shortDescription = "Apple iPhone 15 Pro 128GB",
                        category = "Electronics",
                        unitPrice = 999.99,
                        squareThumbnailURL = "https://example.com/iphone-thumb.jpg",
                        status = 1
                    ),
                    SuggestedProductUiModel(
                        id = "s2",
                        name = "Samsung Galaxy S24",
                        imageURL = "https://example.com/samsung.jpg",
                        price = 899.99,
                        priceText = "₺899,99",
                        shortDescription = "Samsung Galaxy S24 256GB",
                        category = "Electronics",
                        unitPrice = 899.99,
                        squareThumbnailURL = "https://example.com/samsung-thumb.jpg",
                        status = 1
                    )
                ),
                verticalProducts = listOf(
                    VerticalProductsUiModel(
                        id = "v1",
                        name = "Nike Air Max",
                        attribute = "Spor Ayakkabı",
                        imageURL = "https://example.com/nike.jpg",
                        price = 129.99,
                        priceText = "₺129,99"
                    ),
                    VerticalProductsUiModel(
                        id = "v2",
                        name = "Adidas Ultraboost",
                        attribute = "Koşu Ayakkabısı",
                        imageURL = "https://example.com/adidas.jpg",
                        price = 159.99,
                        priceText = "₺159,99"
                    ),
                    VerticalProductsUiModel(
                        id = "v3",
                        name = "Puma RS-X",
                        attribute = "Retro Sneaker",
                        imageURL = "https://example.com/puma.jpg",
                        price = 89.99,
                        priceText = "₺89,99"
                    ),
                    VerticalProductsUiModel(
                        id = "v4",
                        name = "Converse Chuck Taylor",
                        attribute = "Klasik Sneaker",
                        imageURL = "https://example.com/converse.jpg",
                        price = 69.99,
                        priceText = "₺69,99"
                    ),
                    VerticalProductsUiModel(
                        id = "v5",
                        name = "Vans Old Skool",
                        attribute = "Skate Ayakkabısı",
                        imageURL = "https://example.com/vans.jpg",
                        price = 79.99,
                        priceText = "₺79,99"
                    ),
                    VerticalProductsUiModel(
                        id = "v6",
                        name = "New Balance 574",
                        attribute = "Günlük Ayakkabı",
                        imageURL = "https://example.com/newbalance.jpg",
                        price = 99.99,
                        priceText = "₺99,99"
                    )
                ),
                basketItems = listOf(
                    BasketItemUiModel(
                        id = "s1",
                        name = "iPhone 15 Pro",
                        imageURL = "https://example.com/iphone.jpg",
                        attribute = "Apple iPhone 15 Pro 128GB",
                        price = 999.99,
                        priceText = "₺999,99",
                        quantity = 1,
                        totalPrice = 999.99
                    ),
                    BasketItemUiModel(
                        id = "v1",
                        name = "Nike Air Max",
                        imageURL = "https://example.com/nike.jpg",
                        attribute = "Spor Ayakkabı",
                        price = 129.99,
                        priceText = "₺129,99",
                        quantity = 2,
                        totalPrice = 259.98
                    )
                ),
                basketTotal = 1259.97
            ),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> },
            onNavigateToBasket = {}
        )
    }
}

@Preview(name = "Home Content with High Basket Total")
@Composable
private fun HomeContentHighBasketTotalPreview() {
    ComposeProjectTheme {
        HomeContent(
            state = HomeState(
                suggestedProducts = listOf(
                    SuggestedProductUiModel(
                        id = "1",
                        name = "MacBook Pro M3",
                        imageURL = "https://example.com/macbook.jpg",
                        price = 2499.99,
                        priceText = "₺2.499,99",
                        shortDescription = "Apple MacBook Pro M3 14\"",
                        category = "Electronics",
                        unitPrice = 2499.99,
                        squareThumbnailURL = "https://example.com/macbook-thumb.jpg",
                        status = 1
                    )
                ),
                verticalProducts = emptyList(),
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
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> },
            onNavigateToBasket = {}
        )
    }
} 