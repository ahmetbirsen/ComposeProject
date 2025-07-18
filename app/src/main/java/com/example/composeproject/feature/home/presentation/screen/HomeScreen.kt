package com.example.composeproject.feature.home.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeproject.designsysytem.contents.HomeContent
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.feature.home.presentation.HomeState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    state: HomeState,
    isLoading: Boolean = false,
    onRefresh: () -> Unit = {},
    onAddToBasket: (String, String, String, Double, String) -> Unit = { _, _, _, _, _ -> },
    onRemoveFromBasket: (String) -> Unit = { _ -> },
    onNavigateToDetail: (String, String, String, String, Double, String) -> Unit = { _, _, _, _, _, _ -> },
    onNavigateToBasket: () -> Unit = {}
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = onRefresh
    )

    Box {
        HomeContent(
            state = state,
            onAddToBasket = onAddToBasket,
            onRemoveFromBasket = onRemoveFromBasket,
            onNavigateToDetail = onNavigateToDetail,
            onNavigateToBasket = onNavigateToBasket
        )

        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ComposeProjectTheme {
        HomeScreen(
            state = HomeState()
        )
    }
}