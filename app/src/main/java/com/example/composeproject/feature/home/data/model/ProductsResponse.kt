package com.example.composeproject.feature.home.data.model

import com.example.composeproject.core.network.model.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProductsResponse(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("productCount")
    val productCount: Long? = null,
    @SerialName("products")
    val products: ArrayList<Product>? = null,
) : BaseResponse()

@Serializable
internal data class Product(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("attribute")
    val attribute: String? = null,
    @SerialName("imageURL")
    val imageURL: String? = null,
    @SerialName("price")
    val price: Double? = null,
    @SerialName("priceText")
    val priceText: String? = null,
)