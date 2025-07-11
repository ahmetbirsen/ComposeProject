package com.example.composeproject.feature.home.data.service


import com.example.composeproject.feature.home.data.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface HomeService {
    @GET(HomeServiceUrl.PRODUCTS)
    suspend fun getProducts() : Response<ProductsResponse>
}
