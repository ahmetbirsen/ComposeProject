package com.example.composeproject.core.extension


val Int?.orZero: Int
    get() = this ?: 0

val Int?.orUndefined: Int
    get() = this ?: -1

