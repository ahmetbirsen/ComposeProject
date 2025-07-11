package com.example.composeproject.core.network

data class ProjectNetworkException(val networkError: INetworkError) : Exception()
