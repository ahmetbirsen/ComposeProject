package com.example.composeproject.designsysytem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeproject.R
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.TitleLarge
import com.example.composeproject.designsysytem.theme.White
import com.example.composeproject.feature.basket.domain.model.BasketItemUiModel
import com.example.composeproject.feature.detail.DetailState

@Composable
fun DetailBottomBar(
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
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.detail_add_to_basket_button),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = TitleLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBottomBar(){
    ComposeProjectTheme {
        DetailBottomBar(
            state = DetailState(
                basketItem = BasketItemUiModel(
                    id = "1",
                    quantity = 2
                ),
                basketTotal = 100.00,
                productName = "Product Name",
                productAttribute = "Attribute",
                productPriceText = "₺0,00"
            ),
            onAddToBasket = { _, _, _, _, _ -> },
        ) { }
    }
}