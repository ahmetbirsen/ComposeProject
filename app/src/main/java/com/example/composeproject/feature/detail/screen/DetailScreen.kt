package com.example.composeproject.feature.detail.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeproject.R
import com.example.composeproject.designsysytem.components.AppTopBar
import com.example.composeproject.designsysytem.components.BasketTotalBox
import com.example.composeproject.designsysytem.components.DetailBottomBar
import com.example.composeproject.designsysytem.contents.DetailContent
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.White
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import com.example.composeproject.feature.detail.DetailState

@Composable
fun DetailScreen(
    state: DetailState,
    onNavigateBack: () -> Unit = {},
    onNavigateToBasket: () -> Unit = {},
    onAddToBasket: (String, String, String, Double, String) -> Unit = { _, _, _, _, _ -> },
    onRemoveFromBasket: (String) -> Unit = { _ -> }
) {
    Surface(color = Color.White) {
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
                    BasketTotalBox(
                        basketTotal = state.basketTotal,
                        modifier = Modifier
                            .width(100.dp)
                            .height(34.dp)
                            .clickable { onNavigateToBasket() }
                    )
                },
                title = stringResource(R.string.my_basket)
            )
            DetailContent(
                imageUrl = state.productImageUrl,
                productName = state.productName,
                productAttribute = state.productAttribute,
                priceText = state.productPriceText,
                modifier = Modifier.weight(1f)
            )
            DetailBottomBar(
                state = state,
                onAddToBasket = onAddToBasket,
                onRemoveFromBasket = onRemoveFromBasket
            )
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
    }
}

