package com.example.composeproject.core.network.di

import com.example.composeproject.core.network.HeaderInterceptor
import com.example.composeproject.core.qualifiers.ApplicationId
import com.example.composeproject.core.qualifiers.DeviceId
import com.example.composeproject.core.qualifiers.DeviceModel
import com.example.composeproject.core.qualifiers.InterceptorMapKey
import com.example.composeproject.core.qualifiers.InterceptorTypes
import com.example.composeproject.core.qualifiers.Interceptors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Interceptors
    @IntoMap
    @InterceptorMapKey(InterceptorTypes.Header)
    @Suppress("LongParameterList")
    fun provideHeaderInterceptor(
        @DeviceModel deviceModel: String,
        @DeviceId deviceId: String,
        @ApplicationId applicationId: String,
    ): Interceptor {
        return HeaderInterceptor(
            deviceModel = deviceModel,
            deviceId = deviceId,
            applicationId = applicationId,
        )
    }
}
