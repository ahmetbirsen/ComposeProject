package com.example.composeproject.designsysytem.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeproject.designsysytem.components.ProductCard
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.White
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel
import com.example.composeproject.navigation.Routes

@Composable
fun VerticalProductsSection(
    products: List<VerticalProductsUiModel>,
    basketItems: List<BasketItemUiModel>,
    onAddToBasket: (String, String, String, Double, String) -> Unit,
    onRemoveFromBasket: (String) -> Unit,
    onProductClick: (Routes.Detail) -> Unit = { _ -> },
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        items(products, key = { it.id }) { product ->
            ProductCard(
                name = product.name,
                attribute = product.attribute,
                priceText = product.priceText,
                imageUrl = product.imageURL,
                quantity = basketItems.find { it.id == product.id }?.quantity ?: 0,
                onAdd = {
                    onAddToBasket(
                        product.id,
                        product.name,
                        product.imageURL,
                        product.price,
                        product.priceText
                    )
                },
                onRemove = { onRemoveFromBasket(product.id) },
                onProductClick = {
                    onProductClick(Routes.Detail(
                        productId = product.id,
                        name = product.name,
                        attribute = product.attribute,
                        imageUrl = product.imageURL ,
                        price = product.price,
                        priceText = product.priceText
                    ))
                }
            )
        }
    }
}

@Preview(name = "Empty Vertical Products")
@Composable
private fun VerticalProductsSectionEmptyPreview() {
    ComposeProjectTheme {
        VerticalProductsSection(
            products = emptyList(),
            basketItems = emptyList(),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onProductClick = { }
        )
    }
}

@Preview(name = "Single Vertical Product")
@Composable
private fun VerticalProductsSectionSinglePreview() {
    ComposeProjectTheme {
        VerticalProductsSection(
            products = listOf(
                VerticalProductsUiModel(
                    id = "1",
                    name = "Nike Air Max",
                    attribute = "Spor Ayakkabı",
                    imageURL = "https://example.com/nike.jpg",
                    price = 129.99,
                    priceText = "₺129,99"
                )
            ),
            basketItems = emptyList(),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onProductClick = { }
        )
    }
}

@Preview(name = "Multiple Vertical Products")
@Composable
private fun VerticalProductsSectionMultiplePreview() {
    ComposeProjectTheme {
        VerticalProductsSection(
            products = listOf(
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
                ),
                VerticalProductsUiModel(
                    id = "4",
                    name = "Converse Chuck Taylor",
                    attribute = "Klasik Sneaker",
                    imageURL = "https://example.com/converse.jpg",
                    price = 69.99,
                    priceText = "₺69,99"
                ),
                VerticalProductsUiModel(
                    id = "5",
                    name = "Vans Old Skool",
                    attribute = "Skate Ayakkabısı",
                    imageURL = "https://example.com/vans.jpg",
                    price = 79.99,
                    priceText = "₺79,99"
                ),
                VerticalProductsUiModel(
                    id = "6",
                    name = "New Balance 574",
                    attribute = "Günlük Ayakkabı",
                    imageURL = "https://example.com/newbalance.jpg",
                    price = 99.99,
                    priceText = "₺99,99"
                )
            ),
            basketItems = emptyList(),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onProductClick = { }
        )
    }
}

@Preview(name = "Products with Basket Items")
@Composable
private fun VerticalProductsSectionWithBasketPreview() {
    ComposeProjectTheme {
        VerticalProductsSection(
            products = listOf(
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
                ),
                BasketItemUiModel(
                    id = "3",
                    name = "Puma RS-X",
                    imageURL = "https://example.com/puma.jpg",
                    attribute = "Retro Sneaker",
                    price = 89.99,
                    priceText = "₺89,99",
                    quantity = 1,
                    totalPrice = 89.99
                )
            ),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onProductClick = {}
        )
    }
}

@Preview(name = "Many Products (Grid Layout)")
@Composable
private fun VerticalProductsSectionManyProductsPreview() {
    ComposeProjectTheme {
        VerticalProductsSection(
            products = List(12) { index ->
                VerticalProductsUiModel(
                    id = (index + 1).toString(),
                    name = "Ürün ${index + 1}",
                    attribute = "Kategori ${index + 1}",
                    imageURL = "https://example.com/product${index + 1}.jpg",
                    price = (50.0 + index * 10.0),
                    priceText = "₺${50 + index * 10},99"
                )
            },
            basketItems = listOf(
                BasketItemUiModel(
                    id = "1",
                    name = "Ürün 1",
                    imageURL = "https://example.com/product1.jpg",
                    attribute = "Kategori 1",
                    price = 50.0,
                    priceText = "₺50,99",
                    quantity = 1,
                    totalPrice = 50.0
                ),
                BasketItemUiModel(
                    id = "5",
                    name = "Ürün 5",
                    imageURL = "https://example.com/product5.jpg",
                    attribute = "Kategori 5",
                    price = 90.0,
                    priceText = "₺90,99",
                    quantity = 3,
                    totalPrice = 270.0
                )
            ),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onProductClick = {}
        )
    }
} 