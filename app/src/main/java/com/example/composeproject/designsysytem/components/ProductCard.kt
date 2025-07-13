package com.example.composeproject.designsysytem.components

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(40.dp)
                        .offset(x = 12.dp, y = (-12).dp) // Sağa ve yukarıya taşı
                        .shadow(8.dp, RoundedCornerShape(12.dp), clip = false)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .clickable {
                            count++
                            onAdd()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        tint = BrandColor,
                        modifier = Modifier.size(28.dp)
                    )
                }
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
            // Animasyonlu adet ve sil/azaltma alanı
            AnimatedVisibility(
                visible = count > 0,
                enter = expandVertically(animationSpec = tween(300)),
                exit = shrinkVertically(animationSpec = tween(300))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    if (count == 1) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = BrandColor,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    count = 0
                                    onRemove()
                                }
                        )
                    } else if (count > 1) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "Remove",
                            tint = BrandColor,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    count--
                                    onRemove()
                                }
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = count.toString(),
                        color = BrandColor,
                        style = PriceText
                    )
                }
            }
        }
    }
}

@Composable
fun CounterComponent() {
    Box(
        modifier = Modifier
            //.align(Alignment.TopEnd)
            //.size(40.dp)
            //.offset(x = 12.dp, y = (-12).dp) // Sağa ve yukarıya taşı
            .shadow(8.dp, RoundedCornerShape(12.dp), clip = false)
            .background(Color.White, RoundedCornerShape(12.dp))
            .clickable {
//                count++
//                onAdd()
            },
        contentAlignment = Alignment.Center
    ) {
        Column {
            Box(
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = "Add",
                    tint = BrandColor,
                )
            }
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = BrandColor)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                    ,
                    text = "1",
                    color = White,
                    style = CounterText
                )
            }
            Box(
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
//                            count = 0
//                            onRemove()
                        },
                    painter = painterResource(R.drawable.ic_remove),
                    contentDescription = "Delete",
                    tint = BrandColor,
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    Column {
        CounterComponent()
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