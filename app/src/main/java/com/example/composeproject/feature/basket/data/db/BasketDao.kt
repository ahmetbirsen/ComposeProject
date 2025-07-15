package com.example.composeproject.feature.basket.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBasketItem(basketItem: BasketItemEntity)

    @Update
    suspend fun updateBasketItem(basketItem: BasketItemEntity)



    @Query("SELECT * FROM basket_items")
    fun getAllBasketItems(): Flow<List<BasketItemEntity>>

    @Query("SELECT * FROM basket_items WHERE id = :productId")
    suspend fun getBasketItemById(productId: String): BasketItemEntity?

    @Query("DELETE FROM basket_items WHERE id = :productId")
    suspend fun removeBasketItem(productId: String)

    @Query("DELETE FROM basket_items")
    suspend fun clearBasket()

    @Query("SELECT SUM(totalPrice) FROM basket_items")
    fun getBasketTotal(): Flow<Double?>

    @Query("SELECT COUNT(*) FROM basket_items")
    fun getBasketItemCount(): Flow<Int>

    @Transaction
    suspend fun runInTransaction(block: suspend () -> Unit) {
        block()
    }
} 