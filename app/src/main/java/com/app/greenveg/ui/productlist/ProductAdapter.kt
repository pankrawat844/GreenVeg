package com.app.greenveg.ui.productlist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.greenveg.R
import com.app.greenveg.db.AppDatabase
import com.app.greenveg.db.ProductEntity
import com.app.greenveg.product.ProductDetailActivity
import com.app.greenveg.utils.Constants
import com.app.greenveg.utils.toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductAdapter(private val context: Context, private val list: List<ProductEntity>) :
    RecyclerView.Adapter<ProductAdapter.Viewholder>() {

    class Viewholder(view: View) : RecyclerView.ViewHolder(view) {
        val product_name = view.product_name
        val cut_price = view.txtcutprice
        val cut_price_txt = view.txtcutprice_txt
        val price = view.txtprice
        val product_img = view.product_img
        val addtocart = view.add_to_cart
        val buy_now = view.buy_now!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(
            LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        Picasso.get().load(Constants.imageUrl + list[position].productImage1)
            .placeholder(R.drawable.loader).fit().into(holder.product_img)
        holder.product_name.text = list[position].productName
        if (list[position].marketPrice != "") {
            holder.cut_price_txt.visibility = View.VISIBLE
            holder.cut_price.text = list[position].marketPrice
        }
        holder.price.text = "Our Price: Rs " + list[position].sellingPrice
        holder.addtocart.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val productList = AppDatabase(context).cartDao().getCartProduct()
                for (i in productList.iterator()) {
                    if (i.productId == list[position].productId) {
                        withContext(Main) {
                            context.toast("${list[position].productName} already added to basket.")
                        }
                        return@launch
                    }
                }
                if (list[position].unitOfMeasure == "KG") {
                    list[position].selected_quantity = 0.25f
                    list[position].selected_quantity_txt = "250g"
                } else {
                    list[position].selected_quantity = 2f
                    list[position].selected_quantity_txt = "2"
                }
                AppDatabase(context).cartDao().addToCart(list[position])
                withContext(Main) {
                    context.toast("${list[position].productName} added to basket")

                    context.sendBroadcast(Intent("change_value"))
                }
            }
        }
        holder.buy_now.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                val productList = AppDatabase(context).cartDao().getCartProduct()
                for (i in productList.iterator()) {
                    if (i.productId == list[position].productId) {
                        withContext(Main) {
                            context.toast("${list[position].productName} already added to basket.")
                        }
                        return@launch
                    }
                }
                if (list[position].unitOfMeasure == "KG") {
                    list[position].selected_quantity = 0.25f
                    list[position].selected_quantity_txt = "250g"
                } else {
                    list[position].selected_quantity = 2f
                    list[position].selected_quantity_txt = "2"
                }
                AppDatabase(context).cartDao().addToCart(list[position])
//               context.toast("$list[position].productName added to basket")
                val intent = Intent()
                intent.action = "change_value"


                withContext(Main) {
                    context.toast("${list[position].productName} added to basket")

                    context.sendBroadcast(Intent("change_value"))
                }

            }
        }

        holder.product_img.setOnClickListener {
            Intent(context.applicationContext, ProductDetailActivity::class.java).also {
                it.putExtra("data", list[position])
                context.startActivity(it)
            }
        }

    }
}