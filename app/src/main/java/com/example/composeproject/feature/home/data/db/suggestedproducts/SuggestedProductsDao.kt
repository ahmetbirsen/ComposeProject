package com.example.composeproject.feature.home.data.db.suggestedproducts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SuggestedProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuggestedProducts(suggestedProducts: List<SuggestedProductEntity>)

    @Query("SELECT * FROM suggested_products")
    fun getAllSuggestedProducts(): Flow<List<SuggestedProductEntity>>

    @Query("DELETE FROM suggested_products")
    suspend fun deleteAllSuggestedProducts()
}