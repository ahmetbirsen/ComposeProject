package com.example.composeproject.core.extension


import com.example.composeproject.core.model.RestResult
import com.example.composeproject.core.network.INetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

fun <T> Flow<RestResult<T>>.onSuccess(action: suspend (T) -> Unit): Flow<RestResult<T>> {
    return transform { restResult ->
        if (restResult is RestResult.Success) {
            action.invoke(restResult.result)
        }
        emit(restResult)
    }
}

fun <T> Flow<RestResult<T>>.onError(action: suspend (INetworkError) -> Unit): Flow<RestResult<T>> {
    return transform { restResult ->
        if (restResult is RestResult.Error) {
            action.invoke(restResult.error)
        }
        emit(restResult)
    }
}

fun <T> Flow<RestResult<T>>.onEmpty(action: suspend (Boolean) -> T): Flow<RestResult<T>> {
    return transform { restResult ->
        if (restResult is RestResult.Empty) {
            action.invoke(restResult.isSucceeded)
        }
        emit(restResult)
    }
}

fun <T> Flow<RestResult<T>>.onLoading(action: suspend (Boolean) -> Unit): Flow<RestResult<T>> {
    return transform { restResult ->
        if (restResult is RestResult.Loading) {
            action.invoke(restResult.loading)
        }
        emit(restResult)
    }
}

inline fun <T, R> Flow<RestResult<T>>.mapOnSuccess(
    crossinline map: suspend (T?) -> R
): Flow<RestResult<R>> = transform { restResult ->
    val mappedResult = when (restResult) {
        is RestResult.Success -> RestResult.Success(map(restResult.result))

        is RestResult.Empty -> restResult

        is RestResult.Error -> restResult

        is RestResult.Loading -> restResult
    }
    emit(mappedResult)
}
