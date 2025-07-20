@file:Suppress("MagicNumber")

package com.example.composeproject.core.network.di

import android.content.Context
import com.example.composeproject.core.network.NoConnectionInterceptor
import com.example.composeproject.core.network.TimeoutType
import com.example.composeproject.core.qualifiers.BaseUrl
import com.example.composeproject.core.qualifiers.InterceptorMapKey
import com.example.composeproject.core.qualifiers.InterceptorTypes
import com.example.composeproject.core.qualifiers.Interceptors
import com.example.composeproject.buildSrc.BuildType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {

    @Provides
    @Interceptors
    @IntoMap
    @InterceptorMapKey(InterceptorTypes.Connection)
    fun provideNoConnectionInterceptor(@ApplicationContext context: Context): Interceptor {
        return NoConnectionInterceptor(context)
    }

    @Provides
    @Interceptors
    @IntoMap
    @InterceptorMapKey(InterceptorTypes.HttpLogging)
    fun provideLoggingInterceptor(buildType: BuildType): Interceptor {
        val interceptor = HttpLoggingInterceptor()

        interceptor.level = if (buildType.isProd().not()) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Interceptors interceptors: @JvmSuppressWildcards Map<InterceptorTypes, Interceptor>,
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(TimeoutType.DEFAULT_CON_TIMEOUT.timeout, TimeUnit.SECONDS)
            .readTimeout(TimeoutType.DEFAULT_TIMEOUT.timeout, TimeUnit.SECONDS)
            .writeTimeout(TimeoutType.DEFAULT_TIMEOUT.timeout, TimeUnit.SECONDS)
            .followSslRedirects(true)
            .apply {
                interceptors.forEach { addInterceptor(it.value) }
            }

        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        buildType: BuildType
    ): Retrofit {
        val https = "https://"
        val url = if (baseUrl.startsWith(https)) baseUrl else "$https$baseUrl"

        return if (buildType.isProd().not()) {
            Retrofit.Builder()
                .client(
                    okHttpClient.newBuilder()
                        .hostnameVerifier { _, _ -> true }
                        .build()
                )
                .baseUrl(url)
                .addConverterFactory(converterFactory)
                .build()
        } else {
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(converterFactory)
                .build()
        }
    }

    @Provides
    fun provideJsonConverterFactory(json: Json): Converter.Factory {
        return json.asConverterFactory(
            "application/json; charset=UTF-8".toMediaType()
        )
    }
}
