package com.app.greenveg.model


import com.google.gson.annotations.SerializedName

data class ProductList(
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: List<Response>?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Response(
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
        val unitOfMeasure: String?
    )
}