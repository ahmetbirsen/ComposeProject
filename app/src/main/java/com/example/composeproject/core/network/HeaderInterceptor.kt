package com.example.composeproject.core.network


import com.example.composeproject.core.network.util.HeaderConstants
import com.example.composeproject.core.qualifiers.ApplicationId
import com.example.composeproject.core.qualifiers.DeviceId
import com.example.composeproject.core.qualifiers.DeviceModel
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    @DeviceModel private val deviceModel: String,
    @DeviceId private val deviceId: String,
    @ApplicationId private val applicationId: String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader(HeaderConstants.KEY_ENVIRONMENT_ID, deviceId)
        requestBuilder.addHeader(HeaderConstants.KEY_DEVICE_MODEL, deviceModel)
        requestBuilder.addHeader(HeaderConstants.KEY_CONTENT_TYPE, HeaderConstants.CONTENT_TYPE)
        requestBuilder.addHeader(HeaderConstants.KEY_APPLICATION_ID, applicationId)
        requestBuilder.addHeader(HeaderConstants.KEY_PLATFORM, HeaderConstants.PLATFORM)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
