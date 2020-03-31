package com.app.greenveg.ui.history


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HistoryData(
    @SerializedName("data")
    val `data`: Data
) {
    @Keep
    data class Data(
        @SerializedName("response")
        val response: List<HistoryItem>,
        @SerializedName("success")
        val success: Boolean
    )
}