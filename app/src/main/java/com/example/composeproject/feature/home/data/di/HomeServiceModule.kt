package com.example.composeproject.feature.home.data.di

import com.example.composeproject.BuildConfig
import com.example.composeproject.feature.home.data.service.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
internal object HomeServiceModule {

    @Provides
    fun provideHomeService(retrofit: Retrofit): HomeService = retrofit
        .newBuilder()
        .baseUrl(BuildConfig.VERSION_NAME)
        .build()
        .create(HomeService::class.java)
}
