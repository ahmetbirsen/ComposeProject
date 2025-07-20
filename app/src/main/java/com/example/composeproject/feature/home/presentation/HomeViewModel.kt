package com.example.composeproject.feature.home.presentation

import androidx.lifecycle.viewModelScope
import com.example.composeproject.R
import com.example.composeproject.core.CoreViewModel
import com.example.composeproject.core.UiText
import com.example.composeproject.core.extension.onSuccess
import com.example.composeproject.core.network.Error
import com.example.composeproject.core.network.ErrorType
import com.example.composeproject.core.network.LoadingType
import com.example.composeproject.feature.basket.domain.usecase.BasketUseCases
import com.example.composeproject.feature.home.domain.usecase.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val basketUseCases: BasketUseCases
) : CoreViewModel() {

    private val eventChannel = Channel<HomeEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.onStart {
        getVerticalProducts()
        getSuggestedProducts()
        observeBasketItems()
        observeBasketTotal()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _uiState.value
    )

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnRefresh -> {
                refreshData()
            }

            is HomeAction.OnAddToBasket -> {
                addToBasket(
                    productId = action.productId,
                    name = action.name,
                    imageURL = action.imageURL,
                    price = action.price,
                    priceText = action.priceText
                )
            }

            is HomeAction.OnRemoveFromBasket -> {
                removeFromBasket(action.productId)
            }

            else -> Unit
        }
    }

    fun onResume() {
        refreshBasketData()
        refreshData()
    }

    private fun getVerticalProducts() {
        safeFlowApiCall(
            loadingType = LoadingType.FullScreen,
            errorType = ErrorType.Content,
            loadingMessage = UiText.StringResource(R.string.loading)
        ) {
            homeUseCases.getVerticalProducts()
        }.onSuccess { response ->
            _uiState.update { state ->
                state.copy(
                    verticalProducts = response.products
                )
            }
            saveVerticalProductsToDatabase(response.products)
        }.launchIn(viewModelScope)
    }

    private fun getSuggestedProducts() {
        safeFlowApiCall(
            loadingType = LoadingType.Custom,
            errorType = ErrorType.Content,
            loadingMessage = UiText.StringResource(R.string.loading)
        ) {
            homeUseCases.getSuggestedProducts()
        }.onSuccess { response ->
            _uiState.update { state ->
                state.copy(
                    suggestedProducts = response.suggestedProducts
                )
            }
            saveSuggestedProductsToDatabase(response.suggestedProducts)
        }.launchIn(viewModelScope)
    }

    private fun refreshData() {
        viewModelScope.launch {
            try {
                startFullScreenLoading()
                getVerticalProducts()
                getSuggestedProducts()
            } catch (e: Exception) {
                onServiceError(Error.fromException(e))
            } finally {
                stopLoading()
            }
        }
    }

    private fun addToBasket(
        productId: String,
        name: String,
        imageURL: String,
        price: Double,
        priceText: String
    ) {
        viewModelScope.launch {
            try {
                startButtonLoading()
                basketUseCases.addToBasket(productId, name, imageURL, price, priceText)
                    .onEach {
                        refreshBasketData()
                    }
                    .launchIn(viewModelScope)
            } finally {
                stopLoading()
            }
        }
    }

    private fun removeFromBasket(productId: String) {
        viewModelScope.launch {
            try {
                startButtonLoading()
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

    private fun refreshBasketData() {
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

    private fun saveSuggestedProductsToDatabase(suggestedProducts: List<com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel>) {
        homeUseCases.saveSuggestedProductsToDatabase(suggestedProducts)
            .launchIn(viewModelScope)
    }

    private fun saveVerticalProductsToDatabase(verticalProducts: List<com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel>) {
        homeUseCases.saveVerticalProductsToDatabase(verticalProducts)
            .launchIn(viewModelScope)
    }
}