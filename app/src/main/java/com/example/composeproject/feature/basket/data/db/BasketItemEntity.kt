package com.example.composeproject.feature.basket.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_items")
data class BasketItemEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val imageURL: String,
    val price: Double,
    val priceText: String,
    val quantity: Int,
    val totalPrice: Double
) 