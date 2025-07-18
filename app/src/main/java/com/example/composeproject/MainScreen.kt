package com.example.composeproject

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composeproject.core.network.INetworkError
import com.example.composeproject.core.network.NetworkErrorType
import com.example.composeproject.core.network.toError
import com.example.composeproject.designsysytem.components.CustomDialog
import com.example.composeproject.designsysytem.components.DialogType
import com.example.composeproject.navigation.NavigationRoot
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Ana navigation content
        NavigationRoot(navController = navController)
        
        // Global error handler
        NetworkErrorHandler(viewModel)
    }
}



@Composable
fun NetworkErrorHandler(
    viewModel: MainViewModel
) {
    var currentError by remember { mutableStateOf<INetworkError?>(null) }

    // MainViewModel'den gelen error'ları dinle
    LaunchedEffect(Unit) {
        viewModel.errorFlow.collectLatest { error ->
            currentError = error
        }
    }

    // Error dialog - sadece currentError null değilse göster
    currentError?.let { error ->
        CustomDialog(
            dialogType = DialogType.ERROR,
            onDismiss = {
                currentError = null
                viewModel.clearError()
            },
            onConfirm = {
                currentError = null
                viewModel.clearError()
            },
            errorMessage = getErrorMessage(error)
        )
    }
}

private fun getErrorMessage(error: INetworkError?): String {
    return error?.message ?: NetworkErrorType.OTHER.toError().message ?: "Bir hata oluştu. Lütfen tekrar deneyin."
} 