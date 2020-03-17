package com.app.greenveg.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Product")
data class ProductEntity(
        @PrimaryKey(autoGenerate = true) val id: Int?,
        @SerializedName("base_price")
    val basePrice: String?,
        @SerializedName("current_stock_qty")
    val currentStockQty: String?,
        @SerializedName("market_price")
    val marketPrice: String?,
        @SerializedName("price_date")
    val priceDate: String?,
        @SerializedName("product_category")
    val productCategory: String?,
        @SerializedName("product_desc")
    val productDesc: String?,
        @SerializedName("product_id")
    val productId: String?,
        @SerializedName("product_image1")
    val productImage1: String?,
        @SerializedName("product_image2")
    val productImage2: String?,
        @SerializedName("product_image3")
        val productImage3: String?,
        @SerializedName("product_image4")
        val productImage4: String?,
        @SerializedName("product_name")
        val productName: String?,
        @SerializedName("selling_price")
        val sellingPrice: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("unit_of_measure")
        val unitOfMeasure: String?,
        @SerializedName("selected_quantity")
        val selected_quantity: String?
) : Serializable
