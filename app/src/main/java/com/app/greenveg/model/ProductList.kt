package com.app.greenveg.model


import com.app.greenveg.db.ProductEntity
import com.google.gson.annotations.SerializedName

data class ProductList(
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: List<ProductEntity>?,
    @SerializedName("success")
    val success: Boolean?
)