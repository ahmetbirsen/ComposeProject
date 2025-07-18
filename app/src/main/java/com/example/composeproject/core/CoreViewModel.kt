package com.example.composeproject.core

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeproject.R
import com.example.composeproject.core.model.RestResult
import com.example.composeproject.core.network.Error
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

abstract class CoreViewModel : ViewModel() {

    private var loadingCounter = 0

    private val _networkLoadingStateFlow = MutableStateFlow(NetworkStateLoadingState())
    val networkLoadingStateFlow: StateFlow<NetworkStateLoadingState> =
        _networkLoadingStateFlow.asStateFlow()

    open fun startLoading(
        loadingType: LoadingType = LoadingType.Default,
        message: UiText =  UiText.StringResource(R.string.loading)
    ) {
        handleLoading(RestResult.Loading(true), loadingType, message)
    }

    open fun stopLoading() {
        handleLoading(RestResult.Loading(false))
    }

    open fun startFullScreenLoading(
        message: UiText =  UiText.StringResource(R.string.loading)
    ) {
        startLoading(LoadingType.FullScreen, message)
    }

    open fun startButtonLoading(
        message: UiText =  UiText.StringResource(R.string.loading)
    ) {
        startLoading(LoadingType.Button, message)
    }

    open fun onServiceError(error: INetworkError?) {
        viewModelScope.launch {
            val networkStateError = NetworkState.Error(error)
            emitGlobalNetworkState(networkStateError)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun <T : Any> safeFlowApiCall(
        loadingType: LoadingType = LoadingType.Default,
        errorType: ErrorType = ErrorType.Content,
        loadingMessage: UiText,
        call: () -> Flow<RestResult<T>>
    ): Flow<RestResult<T>> {
        return try {
            call.invoke().transform { restResult ->
                handleResult(restResult, errorType, loadingType, loadingMessage)
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

    private suspend fun <T : Any> handleResult(
        restResult: RestResult<T>,
        errorType: ErrorType,
        loadingType: LoadingType,
        loadingMessage: UiText
    ) {
        when (restResult) {
            is RestResult.Empty -> Unit

            is RestResult.Error -> {
                if (errorType != ErrorType.None) {
                    onServiceError(restResult.error)
                }
            }

            is RestResult.Loading -> {
                if (loadingType != LoadingType.None) {
                    handleLoading(restResult, loadingType, loadingMessage)
                }
            }

            is RestResult.Success -> {
                handleSuccessResult(restResult.result)
            }
        }
    }

    private fun <T> onCatch(errorType: ErrorType, exception: Throwable) = flow<RestResult<T>> {
        val error = Error.fromException(exception)

        if (errorType != ErrorType.None) {
            onServiceError(error)
            val errorResult = RestResult.Error(error)
            emit(errorResult)
            return@flow
        }
    }

    private fun handleLoading(
        result: RestResult.Loading,
        loadingType: LoadingType = LoadingType.Default,
        message: UiText =  UiText.StringResource(R.string.loading)
    ) {
        if (result.loading) {
            loadingCounter++
        } else {
            loadingCounter--
        }
        _networkLoadingStateFlow.update {
            it.copy(
                loadingCount = loadingCounter,
                isLoading = loadingCounter > 0,
                loadingType = if (result.loading) loadingType else LoadingType.Default,
                loadingMessage = if (result.loading) message else UiText.StringResource(R.string.loading)
            )
        }
    }

    private suspend fun handleSuccessResult(result: Any, status: IResponseStatus? = null) {
        emitGlobalNetworkState(
            NetworkState.Success(
                args = result,
                status = status
            )
        )
    }

    companion object {
        private val globalNetworkStateChannel = Channel<NetworkState>()
        val globalNetworkStateFlow = globalNetworkStateChannel.receiveAsFlow()

        suspend fun emitGlobalNetworkState(networkState: NetworkState?) {
            if (networkState != null) {
                globalNetworkStateChannel.send(networkState)
            }
        }

        fun clearGlobalNetworkState() {
            globalNetworkStateChannel.trySend(NetworkState.Success(Unit))
        }
    }
}
