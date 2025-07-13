package com.example.composeproject.feature.home.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuggestedProductsResponse(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("products")
    val products: ArrayList<SuggestedProduct>? = null,
)

@Serializable
data class SuggestedProduct(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("imageURL")
    val imageURL: String? = null,
    @SerialName("price")
    val price: Double? = null,
    @SerialName("priceText")
    val priceText: String? = null,
    @SerialName("shortDescription")
    val shortDescription: String? = null,
    @SerialName("category")
    val category: String? = null,
    @SerialName("unitPrice")
    val unitPrice: Double? = null,
    @SerialName("squareThumbnailURL")
    val squareThumbnailURL: String? = null,
    @SerialName("status")
    val status: Int? = null,
)