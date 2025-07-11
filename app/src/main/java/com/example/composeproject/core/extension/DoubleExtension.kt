package com.example.composeproject.core.extension

val Double?.orZero: Double
    get() = this ?: 0.0

val Double?.orUndefined: Double
    get() = this ?: -1.0

