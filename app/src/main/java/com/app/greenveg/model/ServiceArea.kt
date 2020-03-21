package com.app.greenveg.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ServiceArea(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: List<Response>,
    @SerializedName("success")
    val success: Boolean
) {
    @Keep
    data class Response(
        @SerializedName("area_name")
        val areaName: String,
        @SerializedName("id")
        val id: String
    )
}