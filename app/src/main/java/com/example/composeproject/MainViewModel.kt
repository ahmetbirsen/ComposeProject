package com.example.composeproject

import androidx.lifecycle.viewModelScope
import com.example.composeproject.core.CoreViewModel
import com.example.composeproject.core.network.INetworkError
import com.example.composeproject.core.network.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : CoreViewModel() {
    
    // Global error state - Channel kullanarak tek error alıyoruz
    private val errorChannel = Channel<INetworkError?>()
    val errorFlow = errorChannel.receiveAsFlow()
    
    // Global network state'i dinle
    init {
        viewModelScope.launch {
            globalNetworkStateFlow.collect { networkState ->
                when (networkState) {
                    is NetworkState.Error -> {
                        errorChannel.send(networkState.error)
                    }
                    is NetworkState.Success -> {
                        // Success durumunda error'ı temizle
                        errorChannel.send(null)
                    }
                }
            }
        }
    }
    
    fun clearError() {
        errorChannel.trySend(null)
        clearGlobalNetworkState()
    }
} 