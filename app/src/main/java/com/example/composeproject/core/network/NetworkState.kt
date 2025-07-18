package com.example.composeproject.core.network

import com.example.composeproject.R
import com.example.composeproject.core.UiText

sealed interface NetworkState {
    data class Error(val error: INetworkError?) : NetworkState
    data class Success(val args: Any, val status: IResponseStatus? = null) : NetworkState
}

data class NetworkStateLoadingState(
    val loadingCount: Int = 0,
    val isLoading: Boolean = false,
    val loadingType: LoadingType = LoadingType.Default,
    val loadingMessage: UiText = UiText.StringResource(R.string.loading)
)
