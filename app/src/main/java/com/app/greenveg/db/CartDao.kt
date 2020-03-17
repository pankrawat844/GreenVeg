package com.app.greenveg.db

import androidx.room.*

@Dao
interface CartDao {

    @Query("SELECT * FROM Product")
    fun getCartProduct(): List<ProductEntity>

    @Insert
    fun addToCart(item: ProductEntity)

    @Update
    fun updateCart(item: ProductEntity)

    @Delete
    fun deleteFromCart(item: ProductEntity)
}