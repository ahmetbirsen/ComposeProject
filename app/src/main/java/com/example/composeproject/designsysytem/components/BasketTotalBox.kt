package com.example.composeproject.designsysytem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeproject.R
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.Gray
import com.example.composeproject.designsysytem.theme.PriceText
import kotlinx.coroutines.delay


@Composable
fun BasketTotalBox(
    basketTotal: Double,
    modifier: Modifier = Modifier
) {
    var previousTotal by remember { mutableDoubleStateOf(basketTotal) }
    var isAnimating by remember { mutableStateOf(false) }
    
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (isAnimating) Color(0xFFF0F0F0) else Color.White,
        animationSpec = tween(durationMillis = 800),
        label = "BackgroundColorAnimation"
    )
    
    val animatedScale by animateFloatAsState(
        targetValue = if (isAnimating) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 800),
        label = "ScaleAnimation"
    )
    
    LaunchedEffect(basketTotal) {
        if (basketTotal != previousTotal) {
            isAnimating = true
            previousTotal = basketTotal
            delay(800)
            isAnimating = false
        }
    }
    
    Row(
        modifier = modifier
            .defaultMinSize(minWidth = 91.dp, minHeight = 34.dp)
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(if (isAnimating) 3f else 1f)
                .fillMaxHeight()
                .scale(animatedScale)
                .background(animatedBackgroundColor, RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                .padding(horizontal = 4.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(R.drawable.ic_basket),
                contentDescription = "Basket",
                tint = BrandColor,
            )
        }
        
        AnimatedVisibility(
            visible = !isAnimating,
            enter = expandHorizontally(
                animationSpec = tween(durationMillis = 500),
                expandFrom = Alignment.Start
            ) + fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = shrinkHorizontally(
                animationSpec = tween(durationMillis = 500),
                shrinkTowards = Alignment.Start
            ) + fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
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
}

@Preview
@Composable
private fun BasketTotalBoxPreview() {
    BasketTotalBox(100.00)
}