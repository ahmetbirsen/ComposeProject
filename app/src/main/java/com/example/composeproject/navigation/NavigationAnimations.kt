package com.example.composeproject.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

object NavigationAnimations {
    
    /**
     * Slide animasyonu - sağdan sola
     */
    private fun slideInFromRight() = slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = AnimationConfig.NORMAL_EASING
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = AnimationConfig.NORMAL_EASING
        )
    )
    
    private fun slideOutToRight() = slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = AnimationConfig.NORMAL_EASING
        )
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = AnimationConfig.NORMAL_EASING
        )
    )
    
    /**
     * Slide animasyonu - soldan sağa
     */
    private fun slideInFromLeft() = slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(
            durationMillis = AnimationConfig.NORMAL_DURATION,
            easing = AnimationConfig.NORMAL_EASING
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = AnimationConfig.NORMAL_DURATION,
            easing = AnimationConfig.NORMAL_EASING
        )
    )
    
    private fun slideOutToLeft() = slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(
            durationMillis = AnimationConfig.NORMAL_DURATION,
            easing = AnimationConfig.NORMAL_EASING
        )
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = AnimationConfig.NORMAL_DURATION,
            easing = AnimationConfig.NORMAL_EASING
        )
    )
    
    /**
     * Fade animasyonu
     */
    fun fadeIn() = fadeIn(
        animationSpec = tween(
            durationMillis = AnimationConfig.FAST_DURATION,
            easing = AnimationConfig.FAST_EASING
        )
    )
    
    fun fadeOut() = fadeOut(
        animationSpec = tween(
            durationMillis = AnimationConfig.FAST_DURATION,
            easing = AnimationConfig.FAST_EASING
        )
    )
    
    /**
     * Slide up animasyonu
     */
    private fun slideInFromBottom() = slideInVertically(
        initialOffsetY = { it },
        animationSpec = tween(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = AnimationConfig.NORMAL_EASING
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = AnimationConfig.NORMAL_EASING
        )
    )
    
    private fun slideOutToBottom() = slideOutVertically(
        targetOffsetY = { it },
        animationSpec = tween(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = AnimationConfig.NORMAL_EASING
        )
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = AnimationConfig.SLOW_DURATION,
            easing = AnimationConfig.NORMAL_EASING
        )
    )
    
    /**
     * Özel animasyonlar - Screen-specific
     */
    fun homeEnterTransition() = slideInFromLeft()
    fun homeExitTransition() = slideOutToLeft()
    fun homePopEnterTransition() = slideInFromLeft()
    fun homePopExitTransition() = slideOutToLeft()
    
    fun detailEnterTransition() = slideInFromRight()
    fun detailExitTransition() = slideOutToRight()
    fun detailPopEnterTransition() = slideInFromRight()
    fun detailPopExitTransition() = slideOutToRight()
    
    fun basketEnterTransition() = slideInFromBottom()
    fun basketExitTransition() = slideOutToBottom()
    fun basketPopEnterTransition() = slideInFromBottom()
    fun basketPopExitTransition() = slideOutToBottom()
    
    /**
     * Modal animasyonu (bottom sheet benzeri)
     */
    fun modalEnterTransition() = slideInFromBottom()
    fun modalExitTransition() = slideOutToBottom()
    fun modalPopEnterTransition() = slideInFromBottom()
    fun modalPopExitTransition() = slideOutToBottom()
    
    /**
     * Hızlı geçiş animasyonu
     */
    fun quickEnterTransition() = fadeIn(
        animationSpec = tween(
            durationMillis = AnimationConfig.FAST_DURATION,
            easing = AnimationConfig.FAST_EASING
        )
    )
    
    fun quickExitTransition() = fadeOut(
        animationSpec = tween(
            durationMillis = AnimationConfig.FAST_DURATION,
            easing = AnimationConfig.FAST_EASING
        )
    )
} 