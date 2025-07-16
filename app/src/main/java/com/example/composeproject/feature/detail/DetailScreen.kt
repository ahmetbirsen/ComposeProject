package com.example.composeproject.feature.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.composeproject.R
import com.example.composeproject.designsysytem.components.BasketTotalBox
import com.example.composeproject.designsysytem.components.CounterHorizontalComponent
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.PriceText
import com.example.composeproject.designsysytem.theme.TitleLarge
import com.example.composeproject.designsysytem.theme.White
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel

@Composable
fun DetailScreenRoute(
    onNavigateBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState()
    
    DetailScreen(
        state = state.value,
        onNavigateBack = onNavigateBack,
        onAddToBasket = { id, name, imageUrl, price, priceText ->
            viewModel.handleAction(DetailAction.AddToBasket(id, name, imageUrl, price, priceText))
        },
        onRemoveFromBasket = { id ->
            viewModel.handleAction(DetailAction.RemoveFromBasket(id))
        }
    )
}

@Composable
private fun DetailScreen(
    state: DetailState,
    onNavigateBack: () -> Unit = {},
    onAddToBasket: (String, String, String, Double, String) -> Unit = { _, _, _, _, _ -> },
    onRemoveFromBasket: (String) -> Unit = { _ -> }
) {
    Surface(color = Color.White) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar
            DetailTopBar(
                basketTotal = state.basketTotal,
                onNavigateBack = onNavigateBack
            )
            
            // Product Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                
                // Product Image
                AsyncImage(
                    model = state.productImageUrl,
                    contentDescription = state.productName,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Price
                Text(
                    text = state.productPriceText,
                    style = PriceText.copy(color = BrandColor),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Product Name
                Text(
                    text = state.productName,
                    style = TitleLarge,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Product Attribute
                Text(
                    text = state.productAttribute,
                    style = TitleLarge.copy(color = Color.Gray),
                    textAlign = TextAlign.Center
                )
            }
            
            // Bottom Action Bar
            DetailBottomBar(
                state = state,
                onAddToBasket = onAddToBasket,
                onRemoveFromBasket = onRemoveFromBasket
            )
        }
    }
}

@Composable
private fun DetailTopBar(
    basketTotal: Double,
    onNavigateBack: () -> Unit
) {
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
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onNavigateBack() },
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }
            
            Text(
                text = "Ürün Detayı",
                color = White,
                style = TitleLarge,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            
            BasketTotalBox(
                basketTotal = basketTotal,
                modifier = Modifier
                    .width(100.dp)
                    .height(34.dp)
            )
        }
    }
}

@Composable
private fun DetailBottomBar(
    state: DetailState,
    onAddToBasket: (String, String, String, Double, String) -> Unit,
    onRemoveFromBasket: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .shadow(
                elevation = 1.dp,
                spotColor = Color.LightGray,
                ambientColor = Color.LightGray
            ),
    ) {
        if (state.basketItem != null && state.basketItem.quantity > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
            ) {
                CounterHorizontalComponent(
                    modifier = Modifier
                        .height(48.dp)
                        .align(Alignment.Center),
                    count = state.basketItem.quantity,
                    onAdd = {
                        onAddToBasket(
                            state.productId,
                            state.productName,
                            state.productImageUrl,
                            state.productPrice,
                            state.productPriceText
                        )
                    },
                    onRemove = {
                        onRemoveFromBasket(state.productId)
                    },
                    boxSize = 48.dp
                )
            }
        } else {
            Button(
                onClick = {
                    onAddToBasket(
                        state.productId,
                        state.productName,
                        state.productImageUrl,
                        state.productPrice,
                        state.productPriceText
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandColor
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Sepete Ekle",
                    color = White,
                    style = TitleLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    ComposeProjectTheme {
        DetailScreen(
            state = DetailState(
                basketItem = BasketItemUiModel(
                    id = "1",
                    quantity = 2
                ),
                basketTotal = 100.00,
                productName = "Product Name",
                productAttribute = "Attribute",
                productPriceText = "₺0,00"
            )
        )
        DetailBottomBar(
            state = DetailState(
                productName = "Product Name",
                productAttribute = "Attribute",
                productPriceText = "₺0,00"
            ),
            onAddToBasket = { _, _, _, _, _ -> },
            onRemoveFromBasket = {}
        )

    }
}

