package com.example.composeproject.designsysytem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composeproject.designsysytem.theme.ShimmerGray
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun ShimmerHorizontalProductCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = ShimmerGray,
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = ShimmerGray,
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = ShimmerGray,
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = ShimmerGray,
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
            )
        }
    }
} 