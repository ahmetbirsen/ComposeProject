package com.example.composeproject.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object JsonModule {

    @Provides
    fun provideJson(): Json {
        return Json {
            isLenient = true
            prettyPrint = true
            encodeDefaults = false
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }
}
