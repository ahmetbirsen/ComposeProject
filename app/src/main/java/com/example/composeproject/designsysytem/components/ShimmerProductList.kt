package com.example.composeproject.designsysytem.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerVerticalProductList(
    modifier: Modifier = Modifier,
    itemCount: Int = 5
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(7.dp)
    ) {
        items(itemCount) { index ->
            ShimmerProductCard(
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun ShimmerHorizontalProductList(
    modifier: Modifier = Modifier,
    itemCount: Int = 5
) {
    LazyRow(
        modifier = modifier,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)
    ) {
        items(itemCount) { index ->
            ShimmerHorizontalProductCard(
                modifier = Modifier.padding(end = 12.dp)
            )
        }
    }
} 