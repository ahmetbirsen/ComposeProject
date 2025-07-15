package com.example.composeproject.feature.home.data.db.suggestedproducts

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composeproject.feature.basket.data.db.BasketItemEntity
import com.example.composeproject.feature.home.data.db.verticalproducts.VerticalProductEntity

@Database(
    version = 2,
    entities = [SuggestedProductEntity::class, VerticalProductEntity::class, BasketItemEntity::class],
    exportSchema = false
)
abstract class HomeDatabase : RoomDatabase() {

    abstract fun suggestedProductsDao(): SuggestedProductsDao
    abstract fun verticalProductsDao(): com.example.composeproject.feature.home.data.db.verticalproducts.VerticalProductsDao
    abstract fun basketDao(): com.example.composeproject.feature.basket.data.db.BasketDao
} 