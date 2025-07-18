package com.example.composeproject.designsysytem.sections

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.PriceText
import com.example.composeproject.designsysytem.theme.TitleLarge

@Composable
fun ProductInfoSection(
    priceText: String,
    productName: String,
    productAttribute: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = priceText,
        style = PriceText.copy(color = BrandColor),
        textAlign = TextAlign.Center,
        modifier = modifier
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = productName,
        style = TitleLarge,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = productAttribute,
        style = TitleLarge.copy(color = Color.Gray),
        textAlign = TextAlign.Center
    )
}

@Preview(name = "Product Info Section")
@Composable
private fun ProductInfoSectionPreview() {
    ComposeProjectTheme {
        ProductInfoSection(
            priceText = "₺999,99",
            productName = "iPhone 15 Pro",
            productAttribute = "Apple iPhone 15 Pro 128GB"
        )
    }
} 