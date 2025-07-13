package com.example.composeproject.designsysytem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composeproject.R
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.CounterText
import com.example.composeproject.designsysytem.theme.PriceText
import com.example.composeproject.designsysytem.theme.ProductAttributeText
import com.example.composeproject.designsysytem.theme.ProductNameText
import com.example.composeproject.designsysytem.theme.White

@Composable
fun ProductCard(
    name: String,
    attribute: String,
    priceText: String,
    imageUrl: String,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    var count by remember { mutableIntStateOf(0) }
    val isSelected = count > 0
    Box(
        modifier = Modifier
            .width(140.dp)
            .padding(8.dp)
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
                                color = if (isSelected) BrandColor else Color(0xFFE0E0E0)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                )

                // Eski + kutusu kaldırıldı, yerine CounterComponent geldi
                CounterComponent(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 12.dp, y = (-12).dp),
                    count = count,
                    onAdd = {
                        count++
                        onAdd()
                    },
                    onRemove = {
                        if (count > 0) {
                            count--
                            onRemove()
                        }
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

// CounterComponent fonksiyonunu parametreli ve animasyonlu şekilde güncelle
@Composable
fun CounterComponent(
    modifier: Modifier = Modifier,
    count: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(12.dp), clip = false)
            .background(Color.White, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onAdd()
                    }
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = "Add",
                    tint = BrandColor,
                )
            }

            // Eğer ürün eklenmişse, adet ve sil/azalt animasyonlu olarak görünür
            AnimatedVisibility(
                visible = count > 0,
                enter = expandVertically(animationSpec = tween(300)),
                exit = shrinkVertically(animationSpec = tween(300))
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(12.dp))
                ) {
                    // Adet kutusu
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(color = BrandColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = count.toString(),
                            color = White,
                            style = CounterText
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onRemove()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (count == 1) R.drawable.ic_remove else R.drawable.ic_minus
                            ),
                            contentDescription = if (count == 1) "Delete" else "Minus",
                            tint = BrandColor,
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    Column {
        CounterComponent(
            count = 1,
            onAdd = {},
            onRemove = {}
        )
//        ProductCard(
//            name = "Ürün Adı",
//            attribute = "Ürün Özellikleri",
//            imageUrl = "",
//            onAdd = {},
//            onRemove = {},
//            priceText = "₺19.99",
//        )
//        Column {
//            ProductCard(
//                name = "Ürün Adı",
//                attribute = "Ürün Özellikleri",
//                imageUrl = "",
//                onAdd = {},
//                onRemove = {},
//                priceText = "₺19.99",
//            )
//            ProductCard(
//                name = "Ürün Adı",
//                attribute = "Ürün Özellikleri",
//                imageUrl = "",
//                onAdd = {},
//                onRemove = {},
//                priceText = "₺19.99",
//            )
//        }

//        Row {
//            ProductCard(
//                name = "Ürün Adı",
//                attribute = "Ürün Özellikleri",
//                imageUrl = "",
//                onAdd = {},
//                onRemove = {},
//                priceText = "₺19.99",
//            )
//            ProductCard(
//                name = "Ürün Adı",
//                attribute = "Ürün Özellikleri",
//                imageUrl = "",
//                onAdd = {},
//                onRemove = {},
//                priceText = "₺19.99",
//            )
//        }
    }


}