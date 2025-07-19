package com.example.composeproject.feature.detail.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.composeproject.designsysytem.sections.ProductImageSection
import com.example.composeproject.designsysytem.sections.ProductInfoSection
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.White
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import com.example.composeproject.feature.detail.DetailAction
import com.example.composeproject.feature.detail.DetailState

@Composable
fun DetailScreen(
    state: DetailState,
    onAction: (DetailAction) -> Unit = {},
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
                            .clickable { onAction(DetailAction.OnCloseClick) },
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
                            .clickable { onAction(DetailAction.OnBasketBoxClick) }
                    )
                },
                title = stringResource(R.string.my_basket)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ProductImageSection(
                    imageUrl = state.productImageUrl,
                    contentDescription = state.productName
                )
                ProductInfoSection(
                    priceText = state.productPriceText,
                    productName = state.productName,
                    productAttribute = state.productAttribute
                )
            }
            DetailBottomBar(
                state = state,
                onAddToBasket = { productId, name, imageURL, price, priceText ->
                    onAction(
                        DetailAction.OnAddToBasket(
                            productId = productId,
                            name = name,
                            imageURL = imageURL,
                            price = price,
                            priceText = priceText
                        )
                    )
                },
                onRemoveFromBasket = { id ->
                    onAction(DetailAction.OnRemoveFromBasket(id))
                }
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

