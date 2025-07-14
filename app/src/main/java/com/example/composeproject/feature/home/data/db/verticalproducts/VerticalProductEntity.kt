package com.example.composeproject.feature.home.data.db.verticalproducts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vertical_products")
data class VerticalProductEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val attribute: String,
    val imageURL: String,
    val price: Double,
    val priceText: String
) 