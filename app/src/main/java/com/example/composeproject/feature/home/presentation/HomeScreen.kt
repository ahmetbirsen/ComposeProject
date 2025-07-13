package com.example.composeproject.feature.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeproject.ui.theme.ComposeProjectTheme

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        // Bu blok sadece bir kez çalışır
        viewModel.getVerticalProducts()
    }
    Button(
        modifier = Modifier.size(30.dp),
        onClick = {
            viewModel.getVerticalProducts()
        }) {
        Text(text = "Çek")
    }
    HomeScreen(
        state = state.value
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
) {
    Column {
        Text("Hello")
        if (state.verticalProducts.isNotEmpty()) {
            Text(state.verticalProducts.first().name)
        }
        if (state.suggestedProducts.isNotEmpty()) {
            Text(state.suggestedProducts.first().name)
        }
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