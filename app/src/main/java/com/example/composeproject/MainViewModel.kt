package com.example.composeproject

import androidx.lifecycle.viewModelScope
import com.example.composeproject.core.CoreViewModel
import com.example.composeproject.core.network.INetworkError
import com.example.composeproject.core.network.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : CoreViewModel() {
    
    // Global error state
    private val _errorFlow = MutableStateFlow<INetworkError?>(null)
    val errorFlow: StateFlow<INetworkError?> = _errorFlow.asStateFlow()
    
    // Global network state'i dinle
    init {
        viewModelScope.launch {
            globalNetworkStateFlow.collect { networkState ->
                when (networkState) {
                    is NetworkState.Error -> {
                        _errorFlow.value = networkState.error
                    }
                    is NetworkState.Success -> {
                        // Success durumunda error'ı temizle
                        _errorFlow.value = null
                    }
                    null -> {
                        // Null durumunda error'ı temizle
                        _errorFlow.value = null
                    }
                }
            }
        }
    }
    

    
    fun clearError() {
        _errorFlow.value = null
        clearGlobalNetworkState()
    }
} 