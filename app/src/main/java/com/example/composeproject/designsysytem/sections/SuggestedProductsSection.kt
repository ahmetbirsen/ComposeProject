package com.example.composeproject.designsysytem.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeproject.R
import com.example.composeproject.designsysytem.components.ProductCard
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.Gray
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel
import com.example.composeproject.navigation.Routes

@Composable
fun SuggestedProductsSection(
    products: List<SuggestedProductUiModel>,
    basketItems: List<BasketItemUiModel>,
    onAddToBasket: (String, String, String, Double, String) -> Unit,
    onRemoveFromBasket: (String) -> Unit,
    onProductClick: (Routes.Detail) -> Unit = { _ -> },
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Gray)
    ) {
        Text(
            text = stringResource(R.string.suggested_products),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products, key = { it.id }) { product ->
                ProductCard(
                    name = product.name,
                    attribute = product.shortDescription,
                    priceText = product.priceText,
                    imageUrl = product.squareThumbnailURL.ifEmpty { product.imageURL },
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
                            attribute = product.shortDescription,
                            imageUrl = product.squareThumbnailURL.ifEmpty { product.imageURL },
                            price = product.price,
                            priceText = product.priceText
                        ))
                    }
                )
            }
        }
    }
}

@Preview(name = "Empty Suggested Products")
@Composable
private fun SuggestedProductsSectionEmptyPreview() {
    ComposeProjectTheme {
        SuggestedProductsSection(
            products = emptyList(),
            basketItems = emptyList(),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onProductClick =  {}
        )
    }
}

@Preview(name = "Single Suggested Product")
@Composable
private fun SuggestedProductsSectionSinglePreview() {
    ComposeProjectTheme {
        SuggestedProductsSection(
            products = listOf(
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
                )
            ),
            basketItems = emptyList(),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onProductClick =  {}
        )
    }
}

@Preview(name = "Multiple Suggested Products")
@Composable
private fun SuggestedProductsSectionMultiplePreview() {
    ComposeProjectTheme {
        SuggestedProductsSection(
            products = listOf(
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
                ),
                SuggestedProductUiModel(
                    id = "3",
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
                )
            ),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onProductClick = {}
        )
    }
}

@Preview(name = "Products with Basket Items")
@Composable
private fun SuggestedProductsSectionWithBasketPreview() {
    ComposeProjectTheme {
        SuggestedProductsSection(
            products = listOf(
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
                ),
                BasketItemUiModel(
                    id = "2",
                    name = "Samsung Galaxy S24",
                    imageURL = "https://example.com/samsung.jpg",
                    attribute = "Samsung Galaxy S24 256GB",
                    price = 899.99,
                    priceText = "₺899,99",
                    quantity = 3,
                    totalPrice = 2699.97
                )
            ),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = { _ -> },
            onProductClick =  {}
        )
    }
}