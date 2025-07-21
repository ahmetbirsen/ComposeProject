package com.example.composeproject.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.composeproject.core.CoreViewModel
import com.example.composeproject.feature.basket.domain.usecase.AddToBasketUseCase
import com.example.composeproject.feature.basket.domain.usecase.BasketUseCases
import com.example.composeproject.feature.basket.domain.usecase.GetBasketItemsUseCase
import com.example.composeproject.feature.basket.domain.usecase.GetBasketTotalUseCase
import com.example.composeproject.feature.basket.domain.usecase.RemoveFromBasketUseCase
import com.example.composeproject.feature.home.presentation.HomeEvent
import com.example.composeproject.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val basketUseCases: BasketUseCases,
    private val getBasketItemsUseCase: GetBasketItemsUseCase,
    private val getBasketTotalUseCase: GetBasketTotalUseCase,
    savedStateHandle: SavedStateHandle
) : CoreViewModel() {

    private val product: Routes.Detail = savedStateHandle.toRoute<Routes.Detail>()

    private val _uiState = MutableStateFlow(DetailState())
    val uiState: StateFlow<DetailState> = _uiState.asStateFlow()

    private val eventChannel = Channel<DetailEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        initializeProduct(
            productId = product.productId,
            productName = product.name,
            productAttribute = product.attribute,
            productImageUrl = product.imageUrl,
            productPrice = product.price,
            productPriceText = product.priceText
        )
    }

    fun handleAction(action: DetailAction) {
        when (action) {
            is DetailAction.AddToBasket -> {
                addToBasket(
                    action.productId,
                    action.productName,
                    action.productImageUrl,
                    action.productPrice,
                    action.productPriceText
                )
            }

            is DetailAction.RemoveFromBasket -> {
                removeFromBasket(action.productId)
            }

            is DetailAction.OnAddToBasket -> {
                addToBasket(
                    action.productId,
                    action.name,
                    action.imageURL,
                    action.price,
                    action.priceText
                )
            }
            is DetailAction.OnRemoveFromBasket -> {
                removeFromBasket(action.productId)
            }
            else -> Unit
        }
    }

    private fun initializeProduct(
        productId: String,
        productName: String,
        productAttribute: String,
        productImageUrl: String,
        productPrice: Double, productPriceText: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                productId = productId,
                productName = productName,
                productAttribute = productAttribute,
                productImageUrl = productImageUrl,
                productPrice = productPrice,
                productPriceText = productPriceText
            )
            loadBasketData()
        }
    }

    private fun refreshBasketData() {
        val currentProductId = _uiState.value.productId
        basketUseCases.getBasketItems()
            .onEach { basketItems ->
                val currentBasketItem = basketItems.find { it.id == currentProductId }

                _uiState.update { state ->
                    state.copy(basketItem = currentBasketItem)
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

    private fun addToBasket(
        productId: String,
        name: String,
        imageURL: String,
        price: Double,
        priceText: String
    ) {
        basketUseCases.addToBasket(productId, name, imageURL, price, priceText)
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

    private suspend fun loadBasketData() {
        val basketItems = getBasketItemsUseCase.invoke().first()
        val basketTotal = getBasketTotalUseCase.invoke().first()

        val currentProductId = _uiState.value.productId
        val currentBasketItem = basketItems.find { it.id == currentProductId }

        _uiState.value = _uiState.value.copy(
            basketItem = currentBasketItem,
            basketTotal = basketTotal
        )
    }
}

