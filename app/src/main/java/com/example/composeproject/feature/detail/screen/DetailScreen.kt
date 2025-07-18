package com.example.composeproject.feature.detail.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composeproject.R
import com.example.composeproject.designsysytem.components.AppTopBar
import com.example.composeproject.designsysytem.components.BasketTotalBox
import com.example.composeproject.designsysytem.components.CounterHorizontalComponent
import com.example.composeproject.designsysytem.components.DetailBottomBar
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.PriceText
import com.example.composeproject.designsysytem.theme.TitleLarge
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
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                AsyncImage(
                    model = state.productImageUrl,
                    contentDescription = state.productName,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = state.productPriceText,
                    style = PriceText.copy(color = BrandColor),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.productName,
                    style = TitleLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = state.productAttribute,
                    style = TitleLarge.copy(color = Color.Gray),
                    textAlign = TextAlign.Center
                )
            }
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

