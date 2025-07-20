package com.example.composeproject.core.model

import com.example.composeproject.core.network.INetworkError


sealed class RestResult<out T> {

    class Success<out T>(
        val result: T
    ) : RestResult<T>()

    class Empty(val isSucceeded: Boolean) : RestResult<Nothing>()

    class Error(
        val error: INetworkError
    ) : RestResult<Nothing>()

    class Loading(val loading: Boolean) : RestResult<Nothing>()
}

inline fun <T, R> RestResult<T>.mapOnSuccess(map: (T?) -> R): RestResult<R> = when (this) {
    is RestResult.Success -> RestResult.Success(map(result))
    is RestResult.Empty -> this
    is RestResult.Error -> this
    is RestResult.Loading -> this
}
