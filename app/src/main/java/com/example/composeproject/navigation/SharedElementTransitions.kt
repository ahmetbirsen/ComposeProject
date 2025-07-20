package com.example.composeproject.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.navigation.NavBackStackEntry
import androidx.compose.ui.unit.IntOffset

/**
 * Shared element transition için animasyonlar
 */
object SharedElementTransitions {
    
    /**
     * Product card'dan detail screen'e geçiş animasyonu
     */
    fun productToDetailTransition() = slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween<IntOffset>(
            durationMillis = AnimationConfig.DETAIL_DURATION,
            easing = EaseInOut
        )
    ) + fadeIn(
        animationSpec = tween<Float>(
            durationMillis = AnimationConfig.DETAIL_DURATION,
            easing = EaseInOut
        )
    )
    
    fun productToDetailExitTransition() = slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween<IntOffset>(
            durationMillis = AnimationConfig.DETAIL_DURATION,
            easing = EaseInOut
        )
    ) + fadeOut(
        animationSpec = tween<Float>(
            durationMillis = AnimationConfig.DETAIL_DURATION,
            easing = EaseInOut
        )
    )
    
    /**
     * Detail screen'den geri dönüş animasyonu
     */
    fun detailToProductTransition() = slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween<IntOffset>(
            durationMillis = AnimationConfig.DETAIL_DURATION,
            easing = EaseInOut
        )
    ) + fadeIn(
        animationSpec = tween<Float>(
            durationMillis = AnimationConfig.DETAIL_DURATION,
            easing = EaseInOut
        )
    )
    
    fun detailToProductExitTransition() = slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween<IntOffset>(
            durationMillis = AnimationConfig.DETAIL_DURATION,
            easing = EaseInOut
        )
    ) + fadeOut(
        animationSpec = tween<Float>(
            durationMillis = AnimationConfig.DETAIL_DURATION,
            easing = EaseInOut
        )
    )
    
    /**
     * Basket screen'e geçiş animasyonu (modal benzeri)
     */
    fun toBasketTransition() = slideInVertically(
        initialOffsetY = { it },
        animationSpec = tween<IntOffset>(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = EaseInOut
        )
    ) + fadeIn(
        animationSpec = tween<Float>(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = EaseInOut
        )
    )
    
    fun fromBasketTransition() = slideOutVertically(
        targetOffsetY = { it },
        animationSpec = tween<IntOffset>(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = EaseInOut
        )
    ) + fadeOut(
        animationSpec = tween<Float>(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = EaseInOut
        )
    )
    
    /**
     * Home screen'e dönüş animasyonu
     */
    fun toHomeTransition() = slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween<IntOffset>(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = EaseInOut
        )
    ) + fadeIn(
        animationSpec = tween<Float>(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = EaseInOut
        )
    )
    
    fun fromHomeTransition() = slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween<IntOffset>(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = EaseInOut
        )
    ) + fadeOut(
        animationSpec = tween<Float>(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = EaseInOut
        )
    )
} 