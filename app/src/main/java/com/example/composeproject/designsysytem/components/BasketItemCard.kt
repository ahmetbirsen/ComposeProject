package com.example.composeproject.designsysytem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.PriceText
import com.example.composeproject.designsysytem.theme.ProductAttributeText
import com.example.composeproject.designsysytem.theme.SmallTextSBold

@Composable
fun BasketItemCard(
    basketItem: com.example.composeproject.feature.basket.domain.model.BasketItemUiModel,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onProductClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick() },
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            AsyncImage(
                model = basketItem.imageURL,
                contentDescription = basketItem.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        BorderStroke(
                            width = 1.dp,
                            color = Color.LightGray
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Product Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = basketItem.name,
                    style = SmallTextSBold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Attribute", // Placeholder
                    style = ProductAttributeText,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = basketItem.priceText,
                    style = PriceText,
                    color = BrandColor
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Counter Component
            CounterHorizontalComponent(
                count = basketItem.quantity,
                onAdd = onAdd,
                onRemove = onRemove,
                boxSize = 32.dp
            )
        }
    }
}

@Preview
@Composable
private fun BasketItemPreview() {
    ComposeProjectTheme {
        Column {
            BasketItemCard(
                basketItem = com.example.composeproject.feature.basket.domain.model.BasketItemUiModel(
                    id = "1",
                    name = "Ekmek",
                    imageURL = "",
                    price = 10.0,
                    priceText = "₺10.00",
                    quantity = 2
                ),
                onAdd = {},
                onRemove = {},
                onProductClick = {}
            )
        }
    }
}