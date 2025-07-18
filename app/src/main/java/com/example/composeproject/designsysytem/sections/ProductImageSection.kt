package com.example.composeproject.designsysytem.sections

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme

@Composable
fun ProductImageSection(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(24.dp))
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier
            .size(200.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Preview(name = "Product Image Section")
@Composable
private fun ProductImageSectionPreview() {
    ComposeProjectTheme {
        ProductImageSection(
            imageUrl = "https://example.com/product.jpg",
            contentDescription = "Product Image"
        )
    }
} 