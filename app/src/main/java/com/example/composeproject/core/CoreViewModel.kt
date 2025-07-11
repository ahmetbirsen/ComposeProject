package com.example.composeproject.core

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeproject.core.model.RestResult
import com.example.composeproject.core.network.ErrorType
import com.example.composeproject.core.network.INetworkError
import com.example.composeproject.core.network.IResponseStatus
import com.example.composeproject.core.network.LoadingType
import com.example.composeproject.core.network.NetworkState
import com.example.composeproject.core.network.NetworkStateLoadingState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement

abstract class CoreViewModel : ViewModel() {

    private var loadingCounter = 0

    private val networkState = Channel<NetworkState>()
    val networkStateFlow = networkState.receiveAsFlow()

    private val _networkLoadingStateFlow = MutableStateFlow(NetworkStateLoadingState())
    val networkLoadingStateFlow: StateFlow<NetworkStateLoadingState> =
        _networkLoadingStateFlow.asStateFlow()

    open fun startLoading() {
        handleLoading(RestResult.Loading(true))
    }

    open fun stopLoading() {
        handleLoading(RestResult.Loading(false))
    }

    open fun onServiceError(error: INetworkError?) {
        viewModelScope.launch {
            networkState.trySend(
                NetworkState.Error(error)
            )
        }
    }

    protected suspend fun onServiceErrorFlow(error: INetworkError?) {
        networkState.trySend(
            NetworkState.Error(error)
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun <T : Any> safeFlowApiCall(
        loadingType: LoadingType = LoadingType.Default,
        errorType: ErrorType = ErrorType.Content,
        call: () -> Flow<RestResult<T>>
    ): Flow<RestResult<T>> {
        return try {
            call.invoke().transform { restResult ->
                handleResult(restResult, errorType, loadingType)
                emit(restResult)
            }
        } catch (exception: Exception) {
            onCatch(

                errorType = errorType,
                exception = exception
            )
        }.catch {
            onCatch<T>(
                errorType = errorType,
                exception = it
            )
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    suspend fun <T : Any> suspendSafeFlowApiCall(
        loadingType: LoadingType = LoadingType.Default,
        errorType: ErrorType = ErrorType.Content,
        call: suspend () -> Flow<RestResult<T>>
    ): Flow<RestResult<T>> {
        return try {
            call.invoke().transform { restResult ->
                handleResult(restResult, errorType, loadingType)
                emit(restResult)
            }
        } catch (exception: Exception) {
            onCatch(
                errorType = errorType,
                exception = exception
            )
        }.catch {
            onCatch<T>(
                errorType = errorType,
                exception = it
            )
        }
    }

    protected suspend fun <T : Any> handleResult(
        restResult: RestResult<T>,
        errorType: ErrorType,
        loadingType: LoadingType
    ) {
        when (restResult) {
            is RestResult.Empty -> Unit

            is RestResult.Error -> {
                if (errorType != ErrorType.None) {
                    onServiceErrorFlow(restResult.error)
                }
            }

            is RestResult.Loading -> {
                if (loadingType != LoadingType.None) {
                    handleLoading(restResult)
                }
            }

            is RestResult.Success -> {
                handleSuccessResult(restResult.result)
            }
        }
    }

    private fun <T> onCatch(errorType: ErrorType, exception: Throwable) = flow<RestResult<T>> {
        val error = object : INetworkError {
            override var message: String? = exception.message
            override var data: JsonElement? = null
            override var code: Int? = null
            override var externalCode: String? = null
        }

        if (errorType != ErrorType.None) {
            onServiceError(error)
            val errorResult = RestResult.Error(error)
            emit(errorResult)
            return@flow
        }
    }

    fun handleLoading(result: RestResult.Loading) {
        if (result.loading) {
            loadingCounter++
        } else {
            loadingCounter--
        }
        _networkLoadingStateFlow.update {
            it.copy(
                loadingCount = loadingCounter,
                isLoading = loadingCounter > 0
            )
        }
    }

    protected suspend fun handleSuccessResult(result: Any, status: IResponseStatus? = null) {
        networkState.trySend(
            NetworkState.Success(result, status)
        )
    }
}
