package com.app.greenveg.fragment.cart

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.greenveg.R
import com.app.greenveg.db.AppDatabase
import com.app.greenveg.db.ProductEntity
import com.app.greenveg.utils.Constants
import com.app.greenveg.utils.toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartAdapter(val ctx: Context, val list: List<ProductEntity>) :
    RecyclerView.Adapter<CartAdapter.viewHolder>() {

    class viewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val img = itemview.img
        val product_name = itemview.product_name
        val product_price = itemview.price
        val delete = itemview.delete
        val quantity = itemview.quantity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_cart, parent, false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val product = list[position]
        Picasso.get().load(Constants.imageUrl + product.productImage1).into(holder.img)
        holder.product_name.text = product.productName
        holder.product_price.text = "Unit Price: Rs " + product.sellingPrice + " "
        holder.delete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase(ctx).cartDao().deleteFromCart(product)
                withContext(Dispatchers.Main) {
                    ctx.toast("${list[position].productName} remove from basket")
                    ctx.sendBroadcast(Intent("update_cart"))
                }
            }
        }
        if (product.unitOfMeasure == "KG") {
            holder.quantity.adapter = ArrayAdapter(ctx, R.layout.spinner_item, ctx.resources.getStringArray(R.array.kg_array))
        } else {
            holder.quantity.adapter = ArrayAdapter(ctx, R.layout.spinner_item, ctx.resources.getStringArray(R.array.kg_bundle))

        }
        holder.quantity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                CoroutineScope(Dispatchers.IO).launch {

                    AppDatabase(ctx).cartDao().updateCart(product)
                    withContext(Dispatchers.Main) {
                        ctx.sendBroadcast(Intent("update_cart"))
                    }
                }

            }

        }
    }
}