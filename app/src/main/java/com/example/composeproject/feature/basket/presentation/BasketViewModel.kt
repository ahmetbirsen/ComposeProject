package com.example.composeproject.feature.basket.presentation

import androidx.lifecycle.viewModelScope
import com.example.composeproject.R
import com.example.composeproject.core.CoreViewModel
import com.example.composeproject.core.UiText
import com.example.composeproject.core.extension.onError
import com.example.composeproject.core.extension.onSuccess
import com.example.composeproject.core.network.ErrorType
import com.example.composeproject.core.network.LoadingType
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
import kotlinx.coroutines.delay

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


    fun onAction(action: BasketAction) {
        when (action) {
            is BasketAction.OnAddToBasket -> addToBasket(
                productId = action.productId,
                productName = action.productName,
                productImageUrl = action.productImageUrl,
                productPrice = action.productPrice,
                productPriceText = action.productPriceText
            )

            is BasketAction.RemoveFromBasket -> removeFromBasket(action.productId)
            is BasketAction.ShowDialog -> showDialog(action.dialogType)
            is BasketAction.HideDialog -> hideDialog()
            is BasketAction.ClearBasket -> clearBasket()
            is BasketAction.CompleteOrder -> completeOrder()
            else -> Unit
        }
    }

    fun onResume() {
        loadSuggestedProducts()
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
        safeFlowApiCall(
            loadingType = LoadingType.Custom,
            errorType = ErrorType.Content,
            loadingMessage = UiText.StringResource(R.string.loading)
        ) {
            getSuggestedProductsUseCase()
        }.onSuccess { response ->
            _uiState.update { state ->
                state.copy(suggestedProducts = response.suggestedProducts)
            }
        }.onError {
            // Error zaten CoreViewModel tarafından handle edilecek
        }.launchIn(viewModelScope)
    }

    private fun addToBasket(
        productId: String,
        productName: String,
        productImageUrl: String,
        productPrice: Double,
        productPriceText: String
    ) {
        viewModelScope.launch {
            try {
                startButtonLoading(UiText.StringResource(R.string.loading))
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
            catch (e: Exception) {
                eventChannel.trySend(BasketEvent.ShowError(
                    UiText.StringResource(R.string.failed_add_to_basket)
                ))
            }
             finally {
                stopLoading()
            }
        }
    }

    private fun removeFromBasket(productId: String) {
        viewModelScope.launch {
            try {
                startButtonLoading(UiText.StringResource(R.string.loading))
                basketUseCases.removeFromBasket(productId)
                    .onEach {
                        refreshBasketData()
                    }
                    .launchIn(viewModelScope)
            } finally {
                stopLoading()
            }
        }
    }

    private fun clearBasket() {
        viewModelScope.launch {
            try {
                // CoreViewModel loading state'ini başlat
                startLoading()
                delay(2000)
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
            } finally {
                // CoreViewModel loading state'ini bitir
                stopLoading()
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
                startButtonLoading(UiText.StringResource(R.string.loading))
                delay(2000)
                basketUseCases.clearBasket()
                hideDialog()
                eventChannel.trySend(BasketEvent.OrderCompleted)
            } catch (e: Exception) {
                eventChannel.trySend(
                    BasketEvent.ShowError(
                        UiText.StringResource(R.string.failed_complete_order)
                    )
                )
            } finally {
                stopLoading()
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

