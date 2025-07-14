package com.example.composeproject.feature.home.data.db.suggestedproducts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suggested_products")
data class SuggestedProductEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val imageURL: String,
    val price: Double,
    val priceText: String,
    val shortDescription: String,
    val category: String,
    val unitPrice: Double,
    val squareThumbnailURL: String,
    val status: Int?
) 