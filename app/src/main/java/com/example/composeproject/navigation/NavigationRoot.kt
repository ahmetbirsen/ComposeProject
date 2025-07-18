package com.example.composeproject.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.composeproject.MainViewModel
import com.example.composeproject.core.CoreViewModel
import com.example.composeproject.core.network.LoadingType
import com.example.composeproject.designsysytem.components.CustomLoading
import com.example.composeproject.feature.basket.presentation.screen.BasketScreenRoute
import com.example.composeproject.feature.detail.screen.DetailScreenRoute
import com.example.composeproject.feature.home.presentation.screen.HomeScreenRoute
import com.example.composeproject.feature.home.presentation.HomeViewModel
import com.example.composeproject.feature.basket.presentation.BasketViewModel
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Routes.Market
        ) {
            marketGraph(navController)
        }
    }
}

private fun NavGraphBuilder.marketGraph(navController: NavHostController) {
    navigation<Routes.Market>(
        startDestination = Routes.Home,
    ) {
        composable<Routes.Home> {
            val homeViewModel = hiltViewModel<HomeViewModel>()

            HomeScreenRoute(
                onNavigateToDetail = { id, name, attribute, imageUrl, price, priceText ->
                    navController.navigate(
                        Routes.Detail(
                            productId = id,
                            name = name,
                            imageUrl = imageUrl,
                            price = price,
                            priceText = priceText,
                            attribute = attribute
                        )
                    )
                },
                onNavigateToBasket = {
                    navController.navigate(Routes.Basket)
                },
                viewModel = homeViewModel
            )

            LaunchedEffect(Unit) {
                homeViewModel.onResume()
            }

            GlobalLoadingHandler(homeViewModel)
        }

        composable<Routes.Detail> {
            DetailScreenRoute(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToBasket = { navController.navigate(Routes.Basket) }
            )
        }

        composable<Routes.Basket> {
            val basketViewModel = hiltViewModel<BasketViewModel>()

            BasketScreenRoute(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = { id, name, attribute, imageUrl, price, priceText ->
                    navController.navigate(
                        Routes.Detail(
                            productId = id,
                            name = name,
                            imageUrl = imageUrl,
                            price = price,
                            priceText = priceText,
                            attribute = attribute
                        )
                    )
                },
                onNavigateToHome = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Home) { inclusive = true }
                    }
                },
                viewModel = basketViewModel
            )

            LaunchedEffect(Unit) {
                basketViewModel.onResume()
            }

            GlobalLoadingHandler(basketViewModel)
        }
    }
}

@Composable
fun GlobalLoadingHandler(
    coreViewModel: CoreViewModel
) {
    val context = LocalContext.current
    val loadingState = coreViewModel.networkLoadingStateFlow.collectAsState()

    loadingState.value.let { loadingStateValue ->
        when (loadingStateValue.loadingType) {
            LoadingType.FullScreen -> {
                CustomLoading(
                    isLoading = loadingStateValue.isLoading,
                    message = loadingStateValue.loadingMessage.asString(context),
                    isFullScreen = true
                )
            }
            LoadingType.Custom -> {
                CustomLoading(
                    isLoading = loadingStateValue.isLoading,
                    message = loadingStateValue.loadingMessage.asString(context),
                    isFullScreen = false
                )
            }
            LoadingType.Default -> {
                CustomLoading(
                    isLoading = loadingStateValue.isLoading,
                    message = loadingStateValue.loadingMessage.asString(context),
                    isFullScreen = false
                )
            }
            LoadingType.Button -> {
                // Button loading için de aynı loading'i göster
                CustomLoading(
                    isLoading = loadingStateValue.isLoading,
                    message = loadingStateValue.loadingMessage.asString(context),
                    isFullScreen = false
                )
            }
            LoadingType.None -> {
                // Loading gösterme
            }
        }
    }
}