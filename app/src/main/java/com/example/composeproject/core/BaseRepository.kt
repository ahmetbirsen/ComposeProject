package com.example.composeproject.core

import com.example.composeproject.core.model.RestResult
import com.example.composeproject.core.network.NetworkErrorType
import com.example.composeproject.core.network.extension.asRestResult
import com.example.composeproject.core.network.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

open class BaseRepository {

    suspend inline fun <reified T> request(
        crossinline call: suspend () -> Response<T>
    ): RestResult<T> = withContext(Dispatchers.IO) {
        try {
            call.invoke().asRestResult
        } catch (exception: Exception) {
            checkNetworkException(exception)
        }
    }

    fun <T> checkNetworkException(e: Exception): RestResult<T> {
        return if (e.message?.contains("NoInternetConnection") == true) {
            RestResult.Error(Error.from(NetworkErrorType.NO_INTERNET_CONNECTION))
        } else {
            RestResult.Error(Error.from(NetworkErrorType.REQUEST_FAILED))
        }
    }
}
