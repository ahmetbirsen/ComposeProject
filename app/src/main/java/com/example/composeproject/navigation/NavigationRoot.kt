package com.example.composeproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.composeproject.feature.basket.presentation.screen.BasketScreenRoute
import com.example.composeproject.feature.detail.screen.DetailScreenRoute
import com.example.composeproject.feature.home.presentation.screen.HomeScreenRoute
import com.example.composeproject.feature.home.presentation.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Market
    ) {
        marketGraph(navController)
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
            
            // HomeScreen'e geri dönüldüğünde basket verilerini yeniden yükle
            LaunchedEffect(Unit) {
                homeViewModel.onResume()
            }
        }
        
        composable<Routes.Detail> {
            DetailScreenRoute(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable<Routes.Basket> {
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
                }
            )
        }
    }
}