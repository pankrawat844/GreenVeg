package com.app.greenveg.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.greenveg.R
import com.app.greenveg.databinding.ActivityProductlBinding
import com.app.greenveg.db.AppDatabase
import com.app.greenveg.db.ProductEntity
import com.app.greenveg.utils.toast
import kotlinx.android.synthetic.main.activity_productl.*
import kotlinx.android.synthetic.main.toolbar_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailActivity : AppCompatActivity() {
    var list: ArrayList<String>? = ArrayList<String>()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding: ActivityProductlBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_productl)
        val data = intent.getSerializableExtra("data") as ProductEntity
        databinding.data = data
        if (data.productImage1 != "") {
            list?.add(data.productImage1!!)
        }
        if (data.productImage2 != "") {
            list?.add(data.productImage2!!)
        }
        if (data.productImage3 != "") {
            list?.add(data.productImage3!!)
        }
        if (data.productImage4 != "") {
            list?.add(data.productImage4!!)
        }
        val viewPagerAdapter = ProductImageViewPage(this, list!!)
        viewPager.adapter = viewPagerAdapter

        CoroutineScope(Dispatchers.IO).launch {
            val size = AppDatabase(this@ProductDetailActivity).cartDao().getCartProduct().size
            withContext(Dispatchers.Main) {
                cart_item.text = size.toString()
            }
        }

        addtobag.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase(this@ProductDetailActivity).cartDao().addToCart(data)
                val size = AppDatabase(this@ProductDetailActivity).cartDao().getCartProduct().size
                withContext(Dispatchers.Main) {
                    cart_item.text = size.toString()
                    toast("${data.productName} added to basket")


                }
            }
        }

        if (data.unitOfMeasure.equals("Kg")) {
            val adapter =
                ArrayAdapter<SpinnerAdapter>(this, R.layout.spinner_item, R.array.kg_array)
            quantity.adapter = adapter
        } else {

            val adapter =
                ArrayAdapter<SpinnerAdapter>(this, R.layout.spinner_item, R.array.kg_bundle)
            quantity.adapter = adapter

        }

    }
}