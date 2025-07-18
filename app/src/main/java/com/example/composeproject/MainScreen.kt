package com.example.composeproject

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composeproject.core.network.Error
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
    val context = LocalContext.current
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Ana navigation content
        NavigationRoot(navController = navController)
        
        // Global error handler
        NetworkErrorHandler(viewModel)
        
        // Internet bağlantısı kontrolü
        InternetConnectionChecker(context)
    }
}

@Composable
fun InternetConnectionChecker(context: Context) {
    var showErrorDialog by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        // Uygulama açıldığında internet bağlantısını kontrol et
        if (!isInternetAvailable(context)) {
            showErrorDialog = true
        }
    }
    
    if (showErrorDialog) {
        CustomDialog(
            dialogType = DialogType.ERROR,
            onDismiss = {
                showErrorDialog = false
            },
            onConfirm = {
                showErrorDialog = false
            },
            errorMessage = Error.noInternetConnection().message
        )
    }
}

private fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val connection = connectivityManager.getNetworkCapabilities(network)
    
    return connection != null && (
        connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    )
}

@Composable
fun NetworkErrorHandler(
    viewModel: MainViewModel
) {
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // MainViewModel'den gelen error'ları dinle
    LaunchedEffect(Unit) {
        viewModel.errorFlow.collectLatest { error ->
            if (error != null) {
                errorMessage = getErrorMessage(error)
                showErrorDialog = true
            }
        }
    }

    // Error dialog
    if (showErrorDialog) {
        CustomDialog(
            dialogType = DialogType.ERROR,
            onDismiss = {
                showErrorDialog = false
                viewModel.clearError()
            },
            onConfirm = {
                showErrorDialog = false
                viewModel.clearError()
            },
            errorMessage = errorMessage
        )
    }
}

private fun getErrorMessage(error: INetworkError?): String {
    return error?.message ?: NetworkErrorType.OTHER.toError().message ?: "Bir hata oluştu. Lütfen tekrar deneyin."
} 