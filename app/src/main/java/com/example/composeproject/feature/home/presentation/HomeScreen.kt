package com.example.composeproject.feature.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.composeproject.designsysytem.components.ProductCard
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.Gray
import com.example.composeproject.designsysytem.theme.TitleLarge
import com.example.composeproject.designsysytem.theme.PriceText
import com.example.composeproject.designsysytem.theme.ProductNameText
import com.example.composeproject.designsysytem.theme.ProductAttributeText
import com.example.composeproject.designsysytem.theme.White

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState()
    // Sepet state'i örnek olarak burada tutuluyor
    var basketTotal by remember { mutableStateOf(0.0) }
    Surface(color = Gray) {
        HomeScreen(
            state = state.value,
            basketTotal = basketTotal,
            onBasketTotalChange = { basketTotal = it }
        )
    }
}

@Composable
private fun HomeScreen(
    state: HomeState,
    basketTotal: Double,
    onBasketTotalChange: (Double) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        HomeTopBar(basketTotal = basketTotal)
        Spacer(modifier = Modifier.height(8.dp))
        // Horizontal Suggested Products
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = White)
                .padding(vertical = 16.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.suggestedProducts) { product ->
                ProductCard(
                    name = product.name,
                    attribute = product.shortDescription,
                    priceText = product.priceText,
                    imageUrl = product.squareThumbnailURL.ifEmpty { product.imageURL },
                    onAdd = { onBasketTotalChange(basketTotal + product.price) },
                    onRemove = { onBasketTotalChange((basketTotal - product.price).coerceAtLeast(0.0)) }
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        // Vertical Products
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.verticalProducts) { product ->
                ProductCard(
                    name = product.name,
                    attribute = product.attribute,
                    priceText = product.priceText,
                    imageUrl = product.imageURL,
                    onAdd = { onBasketTotalChange(basketTotal + product.price) },
                    onRemove = { onBasketTotalChange((basketTotal - product.price).coerceAtLeast(0.0)) }
                )
            }
        }
    }
}

@Composable
private fun HomeTopBar(basketTotal: Double) {
    Surface(
        color = BrandColor,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Ürünler",
                color = Color.White,
                style = TitleLarge,
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minWidth = 80.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingBasket,
                            contentDescription = "Basket",
                            tint = BrandColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "₺%.2f".format(basketTotal),
                            color = BrandColor,
                            style = PriceText
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ComposeProjectTheme {
        HomeScreen(
            state = HomeState(),
            basketTotal = 0.0,
            onBasketTotalChange = {}
        )
    }
}