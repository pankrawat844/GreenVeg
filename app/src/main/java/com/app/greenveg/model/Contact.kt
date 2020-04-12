package com.app.greenveg.model


import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("data")
    val `data`: Data?
) {
    data class Data(
        @SerializedName("response")
        val response: Response?,
        @SerializedName("success")
        val success: Boolean?
    ) {
        data class Response(
            @SerializedName("email")
            val email: String?,
            @SerializedName("id")
            val id: String?,
            @SerializedName("image")
            val image: String?,
            @SerializedName("mobile")
            val mobile: String?,
            @SerializedName("password")
            val password: String?,
            @SerializedName("role")
            val role: String?,
            @SerializedName("username")
            val username: String?
        )
    }
}