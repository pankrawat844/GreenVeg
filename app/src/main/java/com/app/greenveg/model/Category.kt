package com.app.greenveg.model


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: List<Response>?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Response(
        @SerializedName("category_image")
        val categoryImage: String?,
        @SerializedName("category_name")
        val categoryName: String?,
        @SerializedName("cid")
        val cid: String?,
        @SerializedName("status")
        val status: String?
    )
}