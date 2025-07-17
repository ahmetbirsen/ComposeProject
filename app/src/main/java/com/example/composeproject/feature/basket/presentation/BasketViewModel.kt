package com.example.composeproject.feature.basket.presentation

import androidx.lifecycle.viewModelScope
import com.example.composeproject.R
import com.example.composeproject.core.CoreViewModel
import com.example.composeproject.core.UiText
import com.example.composeproject.core.extension.onError
import com.example.composeproject.core.extension.onSuccess
import com.example.composeproject.designsysytem.components.DialogType
import com.example.composeproject.feature.basket.domain.usecase.BasketUseCases
import com.example.composeproject.feature.home.domain.usecase.GetSuggestedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val basketUseCases: BasketUseCases,
    private val getSuggestedProductsUseCase: GetSuggestedProductsUseCase
) : CoreViewModel() {

    private val eventChannel = Channel<BasketEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(BasketState())
    val uiState = _uiState.onStart {
        loadBasketItems()
        loadSuggestedProducts()
        observeBasketItems()
        observeBasketTotal()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _uiState.value
    )


    fun handleAction(action: BasketAction) {
        when (action) {
            is BasketAction.AddToBasket -> addToBasket(
                action.productId,
                action.productName,
                action.productImageUrl,
                action.productPrice,
                action.productPriceText
            )

            is BasketAction.RemoveFromBasket -> removeFromBasket(action.productId)
            is BasketAction.ShowDialog -> showDialog(action.dialogType)
            is BasketAction.HideDialog -> hideDialog()
            is BasketAction.ClearBasket -> clearBasket()
            is BasketAction.CompleteOrder -> completeOrder()
        }
    }

    private fun loadBasketItems() {
        basketUseCases.getBasketItems()
            .onEach { basketItems ->
                _uiState.update { state ->
                    state.copy(basketItems = basketItems)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadSuggestedProducts() {
        safeFlowApiCall {
            getSuggestedProductsUseCase()
        }.onSuccess { response ->
            _uiState.update { state ->
                state.copy(suggestedProducts = response.suggestedProducts)
            }
        }.onError {
            // Silently fail for suggested products
        }.launchIn(viewModelScope)
    }

    private fun addToBasket(
        productId: String,
        productName: String,
        productImageUrl: String,
        productPrice: Double,
        productPriceText: String
    ) {
        basketUseCases.addToBasket(
            productId,
            productName,
            productImageUrl,
            productPrice,
            productPriceText
        )
            .onEach {
                refreshBasketData()
            }
            .launchIn(viewModelScope)
    }

    private fun removeFromBasket(productId: String) {
        basketUseCases.removeFromBasket(productId)
            .onEach {
                refreshBasketData()
            }
            .launchIn(viewModelScope)
    }

    private fun clearBasket() {
        viewModelScope.launch {
            try {
                basketUseCases.clearBasket()
                hideDialog()
                refreshBasketData()
                eventChannel.trySend(BasketEvent.BasketCleared)
            } catch (e: Exception) {
                eventChannel.trySend(
                    BasketEvent.ShowError(
                        UiText.StringResource(R.string.failed_clear_basket)
                    )
                )
            }
        }
    }

    private fun showDialog(dialogType: DialogType) {
        _uiState.update { it.copy(showDialog = true, dialogType = dialogType) }
    }

    private fun hideDialog() {
        _uiState.update { it.copy(showDialog = false, dialogType = null) }
    }

    private fun completeOrder() {
        viewModelScope.launch {
            try {
                basketUseCases.clearBasket()
                hideDialog()
                eventChannel.trySend(BasketEvent.OrderCompleted)
            } catch (e: Exception) {
                eventChannel.trySend(
                    BasketEvent.ShowError(
                        UiText.StringResource(R.string.failed_complete_order)
                    )
                )
            }
        }
    }

    private fun refreshBasketData() {
        // Basket verilerini yeniden yükle
        basketUseCases.getBasketItems()
            .onEach { basketItems ->
                _uiState.update { state ->
                    state.copy(basketItems = basketItems)
                }
            }
            .launchIn(viewModelScope)

        basketUseCases.getBasketTotal()
            .onEach { total ->
                _uiState.update { state ->
                    state.copy(basketTotal = total)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeBasketItems() {
        basketUseCases.getBasketItems()
            .onEach { basketItems ->
                _uiState.update { state ->
                    state.copy(basketItems = basketItems)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeBasketTotal() {
        basketUseCases.getBasketTotal()
            .onEach { total ->
                _uiState.update { state ->
                    state.copy(basketTotal = total)
                }
            }
            .launchIn(viewModelScope)
    }
}

