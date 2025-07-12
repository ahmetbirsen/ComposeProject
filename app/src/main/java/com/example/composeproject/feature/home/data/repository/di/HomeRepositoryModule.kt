package com.example.composeproject.feature.home.data.repository.di

import com.example.composeproject.feature.home.data.repository.HomeRepositoryImpl
import com.example.composeproject.feature.home.domain.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface HomeRepositoryModule {

    @Binds
    fun bindConfigRepository(impl: HomeRepositoryImpl): HomeRepository
}
