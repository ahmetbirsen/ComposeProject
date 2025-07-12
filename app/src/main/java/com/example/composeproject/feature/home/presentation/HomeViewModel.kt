package com.example.composeproject.feature.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeproject.core.CoreViewModel
import com.example.composeproject.core.extension.onError
import com.example.composeproject.core.extension.onSuccess
import com.example.composeproject.feature.home.domain.usecase.GetVerticalProductsUseCase
import com.example.composeproject.feature.home.domain.usecase.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
    ) : CoreViewModel() {

    private val eventChannel = Channel<HomeEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.onStart {
        //start()
        getVerticalProducts()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = _uiState.value
    )

    init {
        getVerticalProducts()
    }

    fun getVerticalProducts() {
        safeFlowApiCall {
            homeUseCases.getVerticalProducts()
        }.onSuccess { response ->
            _uiState.update { state ->
                state.copy(
                    verticalProducts = response.products
                )
            }
        }.onError {

        }.launchIn(viewModelScope)
    }

}