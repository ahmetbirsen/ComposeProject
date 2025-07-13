package com.example.composeproject.designsysytem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeproject.R
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.Gray
import com.example.composeproject.designsysytem.theme.PriceText


@Composable
fun BasketTotalBox(
    basketTotal: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .defaultMinSize(minWidth = 91.dp, minHeight = 34.dp)
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Sol: Sepet ikonu kutusu (beyaz)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White, RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                .padding(horizontal = 4.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(18.dp), // Küçük ikon!
                painter = painterResource(R.drawable.ic_basket),
                contentDescription = "Basket",
                tint = BrandColor,
            )
        }
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .background(Gray, RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            val priceText = "₺%.2f".format(basketTotal)
            val fontSize = when {
                priceText.length > 8 -> 10.sp
                priceText.length > 6 -> 12.sp
                else -> 14.sp
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = priceText,
                color = BrandColor,
                style = PriceText.copy(fontSize = fontSize),
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun BasketTotalBoxPreview() {
    BasketTotalBox(100.00)
}