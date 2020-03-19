package com.app.greenveg.model

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class User(
    @SerializedName("data")
    val `data`: Data
) {
    @Keep
    data class Data(
            @SerializedName("response")
            val response: Response,
            @SerializedName("success")
            val success: Boolean,
            @SerializedName("msg")
            val msg: String
    ) {
        @Keep
        data class Response(
            @SerializedName("address_line1")
            val addressLine1: String,
            @SerializedName("address_line2")
            val addressLine2: String,
            @SerializedName("address_line3")
            val addressLine3: String,
            @SerializedName("address_line4")
            val addressLine4: String,
            @SerializedName("address_line5")
            val addressLine5: String,
            @SerializedName("addressid")
            val addressid: String,
            @SerializedName("alternate_phone")
            val alternatePhone: String,
            @SerializedName("district")
            val district: String,
            @SerializedName("email")
            val email: String,
            @SerializedName("mobile")
            val mobile: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("password")
            val password: String,
            @SerializedName("pin")
            val pin: String,
            @SerializedName("service_avail_area")
            val serviceAvailArea: String,
            @SerializedName("state")
            val state: String,
            @SerializedName("user_name")
            val userName: String,
            @SerializedName("user_status")
            val userStatus: String,
            @SerializedName("userid")
            val userid: String
        )
    }
}