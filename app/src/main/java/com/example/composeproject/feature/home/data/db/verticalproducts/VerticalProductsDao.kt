package com.example.composeproject.feature.home.data.db.verticalproducts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VerticalProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerticalProducts(verticalProducts: List<VerticalProductEntity>)

    @Query("SELECT * FROM vertical_products")
    fun getAllVerticalProducts(): Flow<List<VerticalProductEntity>>

    @Query("DELETE FROM vertical_products")
    suspend fun deleteAllVerticalProducts()
} 