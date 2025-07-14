package com.example.composeproject.feature.home.data.repository.di

import android.content.Context
import androidx.room.Room
import com.example.composeproject.feature.home.data.db.HomeDatabase
import com.example.composeproject.feature.home.data.repository.HomeRepositoryImpl
import com.example.composeproject.feature.home.domain.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

private const val DB_NAME = "HomeDatabase"

@Module
@InstallIn(ViewModelComponent::class)
internal interface HomeRepositoryModule {

    @Binds
    fun bindConfigRepository(impl: HomeRepositoryImpl): HomeRepository

    companion object {

        @Provides
        fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
            context,
            HomeDatabase::class.java,
            DB_NAME
        ).build()

        @Provides
        fun provideSuggestedProductsDao(db: HomeDatabase) = db.suggestedProductsDao()

        @Provides
        fun provideVerticalProductsDao(db: HomeDatabase) = db.verticalProductsDao()
    }
}
