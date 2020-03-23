package com.app.greenveg.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.app.greenveg.db.ProductEntity

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

    }
}