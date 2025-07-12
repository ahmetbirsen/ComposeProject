package com.example.composeproject.feature.home.data.service

import com.example.composeproject.feature.home.data.model.VerticalProductsResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {
    @GET(HomeServiceUrl.PRODUCTS)
    suspend fun getProducts() : Response<List<VerticalProductsResponse>>
}
