package com.app.greenveg.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {

    @Query("SELECT * FROM Product")
    fun getCartProduct(): List<ProductEntity>

    @Insert
    fun addToCart(item: ProductEntity)

    @Delete
    fun deleteFromCart(item: ProductEntity)
}