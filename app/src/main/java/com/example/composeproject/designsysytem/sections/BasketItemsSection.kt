package com.example.composeproject.designsysytem.sections

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeproject.designsysytem.components.BasketItemCard
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.Gray
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel

@Composable
fun BasketItemsSection(
    basketItems: List<BasketItemUiModel>,
    onAddToBasket: (String, String, String, Double, String) -> Unit,
    onRemoveFromBasket: (String) -> Unit,
    onNavigateToDetail: (String, String, String, String, Double, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(basketItems, key = { it.id }) { basketItem ->
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
                        basketItem.imageURL,
                        basketItem.attribute,
                        basketItem.price,
                        basketItem.priceText
                    )
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        
        if (basketItems.isNotEmpty()) {
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Gray
                )
            }
        }
    }
}

@Preview(name = "Empty Basket Items")
@Composable
private fun BasketItemsSectionEmptyPreview() {
    ComposeProjectTheme {
        BasketItemsSection(
            basketItems = emptyList(),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> }
        )
    }
}

@Preview(name = "Single Basket Item")
@Composable
private fun BasketItemsSectionSinglePreview() {
    ComposeProjectTheme {
        BasketItemsSection(
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
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> }
        )
    }
}

@Preview(name = "Multiple Basket Items")
@Composable
private fun BasketItemsSectionMultiplePreview() {
    ComposeProjectTheme {
        BasketItemsSection(
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
                ),
                BasketItemUiModel(
                    id = "3",
                    name = "Nike Air Max",
                    imageURL = "https://example.com/nike.jpg",
                    attribute = "Spor Ayakkabı",
                    price = 129.99,
                    priceText = "₺129,99",
                    quantity = 3,
                    totalPrice = 389.97
                )
            ),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> }
        )
    }
}

@Preview(name = "Many Basket Items")
@Composable
private fun BasketItemsSectionManyPreview() {
    ComposeProjectTheme {
        BasketItemsSection(
            basketItems = List(8) { index ->
                BasketItemUiModel(
                    id = (index + 1).toString(),
                    name = "Ürün ${index + 1}",
                    imageURL = "https://example.com/product${index + 1}.jpg",
                    attribute = "Kategori ${index + 1}",
                    price = (50.0 + index * 25.0),
                    priceText = "₺${50 + index * 25},99",
                    quantity = (index % 3) + 1,
                    totalPrice = ((50.0 + index * 25.0) * ((index % 3) + 1))
                )
            },
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onNavigateToDetail = { _, _, _, _, _, _ -> }
        )
    }
} 