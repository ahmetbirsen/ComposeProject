package com.example.composeproject.designsysytem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeproject.R
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.SmallTextSBold
import com.example.composeproject.designsysytem.theme.White


@Composable
fun BasketTopBar(
    onNavigateBack: () -> Unit,
    onClearBasket: () -> Unit
) {
    Surface(
        color = BrandColor,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back Button
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onNavigateBack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Title
            Text(
                text = "Sepetim",
                color = White,
                style = SmallTextSBold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            // Clear Basket Button
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClearBasket() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_remove),
                    contentDescription = "Clear Basket",
                    tint = White,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview
@Composable
private fun BasketTopBarPreview() {
    ComposeProjectTheme {
        BasketTopBar(
            onNavigateBack = {},
            onClearBasket = {}
        )
    }
}