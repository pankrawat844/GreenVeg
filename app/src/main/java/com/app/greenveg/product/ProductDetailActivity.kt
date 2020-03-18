package com.app.greenveg.product

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.greenveg.R
import com.app.greenveg.databinding.ActivityProductlBinding
import com.app.greenveg.db.AppDatabase
import com.app.greenveg.db.ProductEntity
import com.app.greenveg.fragment.cart.CartActivity
import com.app.greenveg.utils.toast
import kotlinx.android.synthetic.main.activity_productl.*
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

        back.setOnClickListener {
            onBackPressed()
        }
        addtobag.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val productList = AppDatabase(this@ProductDetailActivity).cartDao().getCartProduct()
                for (i in productList.iterator()) {
                    if (i.productId == data.productId) {
                        withContext(Dispatchers.Main) {
                            toast("${data.productName} already added to basket.")
                        }
                        return@launch
                    }
                }

//                CoroutineScope(Dispatchers.IO).launch {
//
//                    AppDatabase(ctx).cartDao().updateCart(product)
//                    withContext(Dispatchers.Main) {
//                        ctx.sendBroadcast(Intent("update_cart"))
//                    }
//                }

                AppDatabase(this@ProductDetailActivity).cartDao().addToCart(data)
                val size = AppDatabase(this@ProductDetailActivity).cartDao().getCartProduct().size
                withContext(Dispatchers.Main) {
                    cart_item.text = size.toString()
                    toast("${data.productName} added to basket")
                    sendBroadcast(Intent("change_value"))

                }
            }
        }


        button1.setOnClickListener {
            Intent(this, CartActivity::class.java).also {
                startActivity(it)
            }
        }

        buy_now.setOnClickListener {
            addtobag.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {
                    val productList =
                            AppDatabase(this@ProductDetailActivity).cartDao().getCartProduct()
                    for (i in productList.iterator()) {
                        if (i.productId == data.productId) {
                            withContext(Dispatchers.Main) {
                                toast("${data.productName} already added to basket.")
                            }
                            return@launch
                        }
                    }
                    AppDatabase(this@ProductDetailActivity).cartDao().addToCart(data)
                    val size =
                            AppDatabase(this@ProductDetailActivity).cartDao().getCartProduct().size
                    withContext(Dispatchers.Main) {
                        cart_item.text = size.toString()
                        toast("${data.productName} added to basket")
                        sendBroadcast(Intent("change_value"))

                    }
                }
            }
        }
        if (data.unitOfMeasure.equals("KG")) {
            val adapter =
                    ArrayAdapter(
                            this,
                            R.layout.spinner_item,
                            resources.getStringArray(R.array.kg_array)
                    )
            quantity.adapter = adapter
        } else {

            val adapter =
                    ArrayAdapter(
                            this,
                            R.layout.spinner_item,
                            resources.getStringArray(R.array.kg_bundle)
                    ) as SpinnerAdapter
            quantity.adapter = adapter

        }

        quantity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (data.unitOfMeasure == "KG") {
                    if (parent?.selectedItem.toString() == "250g") {
                        data.selected_quantity = 0.25f
                    } else if (parent?.selectedItem.toString() == "500g") {
                        data.selected_quantity = 0.50f
                    } else if (parent?.selectedItem.toString() == "750g") {
                        data.selected_quantity = 0.75f
                    } else if (parent?.selectedItem.toString() == "1kg") {
                        data.selected_quantity = 1.00f
                    } else if (parent?.selectedItem.toString() == "1.25kg") {
                        data.selected_quantity = 1.25f
                    } else if (parent?.selectedItem.toString() == "1.5kg") {
                        data.selected_quantity = 1.50f
                    } else if (parent?.selectedItem.toString() == "1.75kg") {
                        data.selected_quantity = 1.75f
                    } else if (parent?.selectedItem.toString() == "2kg") {
                        data.selected_quantity = 2.00f
                    } else if (parent?.selectedItem.toString() == "2.25kg") {
                        data.selected_quantity = 2.25f
                    } else if (parent?.selectedItem.toString() == "2.5kg") {
                        data.selected_quantity = 2.50f
                    } else if (parent?.selectedItem.toString() == "2.75kg") {
                        data.selected_quantity = 2.75f
                    } else if (parent?.selectedItem.toString() == "3kg") {
                        data.selected_quantity = 3.00f
                    } else if (parent?.selectedItem.toString() == "3.25kg") {
                        data.selected_quantity = 3.25f
                    } else if (parent?.selectedItem.toString() == "3.5kg") {
                        data.selected_quantity = 3.50f
                    } else if (parent?.selectedItem.toString() == "3.75kg") {
                        data.selected_quantity = 3.75f
                    }
                } else {
                    data.selected_quantity =
                            parent?.selectedItem.toString().toFloatOrNull()!!
                }
                data.selected_quantity_txt = parent?.selectedItem.toString()
            }

        }
    }
}