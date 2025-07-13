package com.example.composeproject.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeproject.designsysytem.components.BasketTotalBox
import com.example.composeproject.designsysytem.components.ProductCard
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.Gray
import com.example.composeproject.designsysytem.theme.TitleLarge
import com.example.composeproject.designsysytem.theme.White

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState()
    // Sepet state'i örnek olarak burada tutuluyor
    var basketTotal by remember { mutableDoubleStateOf(0.0) }
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
            items(state.suggestedProducts, key = { it.id }) { product ->
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            items(state.verticalProducts, key = { it.id }) { product ->
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
            BasketTotalBox(
                basketTotal = basketTotal,
                modifier = Modifier
                    .width(100.dp)
                    .height(34.dp)
                    .align(Alignment.CenterVertically)
            )
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