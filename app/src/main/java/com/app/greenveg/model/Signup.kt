package com.app.greenveg.model


import com.google.gson.annotations.SerializedName

data class Signup(
    @SerializedName("data")
    val `data`: Data?
) {
    data class Data(
        @SerializedName("msg")
        val msg: String?,
        @SerializedName("success")
        val success: Boolean?
    )
}