package com.example.composeproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.composeproject.feature.detail.DetailScreenRoute
import com.example.composeproject.feature.home.presentation.HomeScreenRoute


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
                }
            )
        }

        composable<Routes.Detail> {
            DetailScreenRoute(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}