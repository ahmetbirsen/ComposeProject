package com.example.composeproject.feature.home.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composeproject.feature.home.data.db.suggestedproducts.SuggestedProductEntity
import com.example.composeproject.feature.home.data.db.suggestedproducts.SuggestedProductsDao
import com.example.composeproject.feature.home.data.db.verticalproducts.VerticalProductEntity

@Database(
    version = 1,
    entities = [SuggestedProductEntity::class, VerticalProductEntity::class],
    exportSchema = false

)
abstract class HomeDatabase : RoomDatabase() {

    abstract fun suggestedProductsDao(): SuggestedProductsDao
    abstract fun verticalProductsDao(): com.example.composeproject.feature.home.data.db.verticalproducts.VerticalProductsDao
}