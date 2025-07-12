package com.example.composeproject.feature.home.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerticalProductsResponse(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("productCount")
    val productCount: Long? = null,
    @SerialName("products")
    val products: ArrayList<Product>? = null,
)

@Serializable
data class Product(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("attribute")
    val attribute: String? = null,
    @SerialName("thumbnailURL")
    val thumbnailURL: String? = null,
    @SerialName("imageURL")
    val imageURL: String? = null,
    @SerialName("price")
    val price: Double? = null,
    @SerialName("priceText")
    val priceText: String? = null,
)