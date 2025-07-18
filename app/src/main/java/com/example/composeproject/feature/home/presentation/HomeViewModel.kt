package com.example.composeproject.feature.home.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.composeproject.core.CoreViewModel
import com.example.composeproject.core.extension.onError
import com.example.composeproject.core.extension.onSuccess
import com.example.composeproject.core.network.ErrorType
import com.example.composeproject.core.network.LoadingType
import com.example.composeproject.feature.basket.domain.usecase.BasketUseCases
import com.example.composeproject.feature.home.domain.usecase.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlinx.coroutines.launch

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

    fun onResume() {
        refreshBasketData()
        // Internet bağlantısı kontrolü için network çağrısı yap
        refreshData()
    }

    private fun getVerticalProducts() {
        safeFlowApiCall(
            loadingType = LoadingType.FullScreen,
            errorType = ErrorType.Content,
            loadingMessage = "Yükleniyor..."
        ) {
            homeUseCases.getVerticalProducts()
        }.onSuccess { response ->
            _uiState.update { state ->
                state.copy(
                    verticalProducts = response.products
                )
            }
            saveVerticalProductsToDatabase(response.products)
        }.onError {
            // Error zaten CoreViewModel tarafından handle edilecek
        }.launchIn(viewModelScope)
    }

    private fun getSuggestedProducts() {
        safeFlowApiCall(
            loadingType = LoadingType.Custom,
            errorType = ErrorType.Content,
            loadingMessage = "Yükleniyor..."
        ) {
            homeUseCases.getSuggestedProducts()
        }.onSuccess { response ->
            _uiState.update { state ->
                state.copy(
                    suggestedProducts = response.suggestedProducts
                )
            }
             saveSuggestedProductsToDatabase(response.suggestedProducts)
        }.onError {
            // Error zaten CoreViewModel tarafından handle edilecek
        }.launchIn(viewModelScope)
    }

    fun refreshData() {
        viewModelScope.launch {
            try {
                startFullScreenLoading("Yükleniyor...")
                getVerticalProducts()
                getSuggestedProducts()
            } finally {
                stopLoading()
            }
        }
    }

    fun addToBasket(productId: String, name: String, imageURL: String, price: Double, priceText: String) {
        viewModelScope.launch {
            try {
                startButtonLoading("Yükleniyor...")
                basketUseCases.addToBasket(productId, name, imageURL, price, priceText)
                    .onEach {
                        // Basket işlemi tamamlandıktan sonra UI'ı güncelle
                        refreshBasketData()
                    }
                    .launchIn(viewModelScope)
            } finally {
                stopLoading()
            }
        }
    }

    fun removeFromBasket(productId: String) {
        viewModelScope.launch {
            try {
                startButtonLoading("Yükleniyor...")
                basketUseCases.removeFromBasket(productId)
                    .onEach {
                        // Basket işlemi tamamlandıktan sonra UI'ı güncelle
                        refreshBasketData()
                    }
                    .launchIn(viewModelScope)
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

    private fun saveSuggestedProductsToDatabase(suggestedProducts: List<com.example.composeproject.feature.home.domain.model.SuggestedProductUiModel>) {
        homeUseCases.saveSuggestedProductsToDatabase(suggestedProducts)
            .launchIn(viewModelScope)
    }

    private fun saveVerticalProductsToDatabase(verticalProducts: List<com.example.composeproject.feature.home.domain.model.VerticalProductsUiModel>) {
        homeUseCases.saveVerticalProductsToDatabase(verticalProducts)
            .launchIn(viewModelScope)
    }
}