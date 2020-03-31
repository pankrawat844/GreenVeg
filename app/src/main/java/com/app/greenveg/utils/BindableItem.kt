package com.app.greenveg.utils

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.app.greenveg.db.ProductEntity
import java.text.SimpleDateFormat
import java.util.*

class BindableItem {
    companion object {
        @JvmStatic
        @BindingAdapter("productname")
        fun productName(txtView: TextView, data: ProductEntity) {
            if (data.unitOfMeasure == "KG") {
                txtView.text = "Rs " + data.sellingPrice + " per kg"
            } else if (data.unitOfMeasure == "NUMBER") {
                txtView.text = "Rs " + data.sellingPrice + " per no"
            } else if (data.unitOfMeasure == "BUNDLE") {
                txtView.text = "Rs " + data.sellingPrice + " per bundle"
            }
        }

        @SuppressLint("SetTextI18n")
        @JvmStatic
        @BindingAdapter("changeOrderDate")
        fun changeOrderDate(txtView: TextView, date: String) {
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val changeDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val newDate = dateFormat.parse(date)

            txtView.text = "Order Date: " + changeDateFormat.format(newDate!!)
        }

        @SuppressLint("SetTextI18n")
        @JvmStatic
        @BindingAdapter("changeDeliveryDate")
        fun changeDeliveryDate(txtView: TextView, date: String) {
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val changeDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val newDate = dateFormat.parse(date)

            txtView.text = "Delivery Date: " + changeDateFormat.format(newDate!!)
        }

    }
}