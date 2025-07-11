package com.example.composeproject.di

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.example.composeproject.BuildConfig
import com.example.composeproject.buildSrc.BuildType
import com.example.composeproject.core.qualifiers.ApplicationId
import com.example.composeproject.core.qualifiers.BaseUrl
import com.example.composeproject.core.qualifiers.DeviceId
import com.example.composeproject.core.qualifiers.DeviceModel
import com.example.composeproject.util.CommonUtils
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindBuildTypes(types: CommonUtils): BuildType

    companion object {

        @BaseUrl
        @Provides
        fun provideBaseUrl(): String {
            return if (BuildConfig.DEBUG) {
                "https://test/"
            } else {
                "https://prod/"
            }
        }

        @DeviceModel
        @Provides
        fun provideDeviceModel(): String = Build.MODEL

        @DeviceId
        @Provides
        fun provideDeviceId(@ApplicationContext context: Context): String =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

        @ApplicationId
        @Provides
        fun provideApplicationId() = BuildConfig.APPLICATION_ID
    }
}
