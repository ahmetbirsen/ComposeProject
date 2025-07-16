package com.example.composeproject.designsysytem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.PriceText
import com.example.composeproject.designsysytem.theme.ProductAttributeText
import com.example.composeproject.designsysytem.theme.ProductNameText

@Composable
fun ProductCard(
    name: String,
    attribute: String,
    priceText: String,
    imageUrl: String,
    quantity: Int = 0,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onProductClick: () -> Unit = {}
) {
    val isSelected = quantity > 0
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) BrandColor else Color(0xFFE0E0E0),
        animationSpec = tween(durationMillis = 300),
        label = "BorderColorAnimation"
    )

    Box(
        modifier = Modifier
            .width(140.dp)
            .padding(8.dp)
            .clickable { onProductClick() }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Ürün görseli veya placeholder
                AsyncImage(
                    model = imageUrl,
                    contentDescription = name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(92.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            BorderStroke(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = borderColor
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                )

                // Eski + kutusu kaldırıldı, yerine CounterComponent geldi
                CounterComponent(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 12.dp, y = (-12).dp),
                    count = quantity,
                    onAdd = {
                        onAdd()
                    },
                    onRemove = {
                        onRemove()
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = priceText,
                color = BrandColor,
                style = PriceText
            )
            Text(
                text = name,
                color = Color.Black,
                style = ProductNameText,
                maxLines = 2,
                modifier = Modifier.padding(top = 2.dp)
            )
            Text(
                text = attribute,
                color = Color.Gray,
                style = ProductAttributeText,
                maxLines = 1,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    Column {
        ProductCard(
            name = "Ürün Adı",
            attribute = "Ürün Özellikleri",
            imageUrl = "",
            quantity = 0,
            onAdd = {},
            onRemove = {},
            priceText = "₺19.99",
        )
    }
}