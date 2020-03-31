package com.app.greenveg.ui.history


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HistoryItem(
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
    @SerializedName("delivery_address_id")
    val deliveryAddressId: String,
    @SerializedName("delivery_date")
    val deliveryDate: String,
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("district")
    val district: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("landmark")
    val landmark: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order_date")
    val orderDate: String,
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("order_status")
    val orderStatus: String,
    @SerializedName("order_total")
    val orderTotal: String,
    @SerializedName("pincode")
    val pincode: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("product_order_qnty")
    val productOrderQnty: String,
    @SerializedName("product_price")
    val productPrice: String,
    @SerializedName("product_unit_rate")
    val productUnitRate: String,
    @SerializedName("service_area")
    val serviceArea: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("total_price")
    val totalPrice: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String
)