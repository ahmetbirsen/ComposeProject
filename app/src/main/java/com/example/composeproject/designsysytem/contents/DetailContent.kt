package com.example.composeproject.designsysytem.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeproject.designsysytem.sections.ProductImageSection
import com.example.composeproject.designsysytem.sections.ProductInfoSection
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme

@Composable
fun DetailContent(
    imageUrl: String,
    productName: String,
    productAttribute: String,
    priceText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProductImageSection(
            imageUrl = imageUrl,
            contentDescription = productName
        )
        ProductInfoSection(
            priceText = priceText,
            productName = productName,
            productAttribute = productAttribute
        )
    }
}

@Preview(name = "Detail Content")
@Composable
private fun DetailContentPreview() {
    ComposeProjectTheme {
        DetailContent(
            imageUrl = "https://example.com/iphone.jpg",
            productName = "iPhone 15 Pro",
            productAttribute = "Apple iPhone 15 Pro 128GB",
            priceText = "₺999,99"
        )
    }
} 