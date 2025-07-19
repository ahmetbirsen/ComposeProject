package com.example.composeproject.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.composeproject.core.CoreViewModel
import com.example.composeproject.core.network.LoadingType
import com.example.composeproject.designsysytem.components.CustomLoading
import com.example.composeproject.feature.basket.presentation.BasketViewModel
import com.example.composeproject.feature.basket.presentation.screen.BasketScreenRoute
import com.example.composeproject.feature.detail.screen.DetailScreenRoute
import com.example.composeproject.feature.home.presentation.HomeViewModel
import com.example.composeproject.feature.home.presentation.screen.HomeScreenRoute


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
        composable<Routes.Home>(
            enterTransition = { NavigationAnimations.homeEnterTransition() },
            exitTransition = { NavigationAnimations.homeExitTransition() },
            popEnterTransition = { NavigationAnimations.homePopEnterTransition() },
            popExitTransition = { NavigationAnimations.homePopExitTransition() }
        ) {
            val homeViewModel = hiltViewModel<HomeViewModel>()

            HomeScreenRoute(
                onProductClick = { product ->
                    navController.navigate(Routes.Detail(
                        productId = product.productId,
                        name = product.name,
                        attribute = product.attribute,
                        imageUrl = product.imageUrl,
                        price = product.price,
                        priceText = product.priceText
                    ))
                },
                onBasketBoxClick = {
                    navController.navigate(Routes.Basket)
                },
            )

            LaunchedEffect(Unit) {
                homeViewModel.onResume()
            }

            GlobalLoadingHandler(homeViewModel)
        }

        composable<Routes.Detail>(
            enterTransition = { SharedElementTransitions.productToDetailTransition() },
            exitTransition = { SharedElementTransitions.productToDetailExitTransition() },
            popEnterTransition = { SharedElementTransitions.detailToProductTransition() },
            popExitTransition = { SharedElementTransitions.detailToProductExitTransition() }
        ) {
            DetailScreenRoute(
                onCloseClick = { navController.navigateUp() },
                onBasketBoxClick = { navController.navigate(Routes.Basket) }
            )
        }

        composable<Routes.Basket>(
            enterTransition = { SharedElementTransitions.toBasketTransition() },
            exitTransition = { SharedElementTransitions.fromBasketTransition() },
            popEnterTransition = { SharedElementTransitions.toBasketTransition() },
            popExitTransition = { SharedElementTransitions.fromBasketTransition() }
        ) {
            val basketViewModel = hiltViewModel<BasketViewModel>()

            BasketScreenRoute(
                onNavigateToHome = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Home) { inclusive = true }
                    }
                },
                onProductClick = { product ->
                    navController.navigate(Routes.Detail(
                        productId = product.productId,
                        name = product.name,
                        attribute = product.attribute,
                        imageUrl = product.imageUrl,
                        price = product.price,
                        priceText = product.priceText
                    ))
                },
                onCloseClick = {
                    navController.navigateUp()
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