package com.example.composeproject.navigation

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween

/**
 * Navigation animasyonları için konfigürasyon
 */
object AnimationConfig {
    
    // Animasyon süreleri
    const val FAST_DURATION = 200
    const val NORMAL_DURATION = 300
    const val SLOW_DURATION = 400
    const val DETAIL_DURATION = 500
    
    // Easing fonksiyonları
    val FAST_EASING = EaseOut
    val NORMAL_EASING = EaseInOut
    val SMOOTH_EASING = FastOutSlowInEasing
    
    // Tween animasyonları
    fun fastTween() = tween<Float>(
        durationMillis = FAST_DURATION,
        easing = FAST_EASING
    )
    
    fun normalTween() = tween<Float>(
        durationMillis = NORMAL_DURATION,
        easing = NORMAL_EASING
    )
    
    fun slowTween() = tween<Float>(
        durationMillis = SLOW_DURATION,
        easing = NORMAL_EASING
    )
    
    fun detailTween() = tween<Float>(
        durationMillis = DETAIL_DURATION,
        easing = SMOOTH_EASING
    )
    
    // Animasyon tipleri
    enum class AnimationType {
        SLIDE_HORIZONTAL,
        SLIDE_VERTICAL,
        FADE,
        SCALE,
        COMBINED
    }
    
    // Screen-specific animasyonlar
    object HomeAnimations {
        val enterDuration = NORMAL_DURATION
        val exitDuration = NORMAL_DURATION
        val type = AnimationType.SLIDE_HORIZONTAL
    }
    
    object DetailAnimations {
        val enterDuration = DETAIL_DURATION
        val exitDuration = DETAIL_DURATION
        val type = AnimationType.COMBINED
    }
    
    object BasketAnimations {
        val enterDuration = SLOW_DURATION
        val exitDuration = SLOW_DURATION
        val type = AnimationType.SLIDE_VERTICAL
    }
} 