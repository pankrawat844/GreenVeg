package com.app.greenveg.fragment.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.greenveg.R
import com.app.greenveg.db.ProductEntity
import com.app.greenveg.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cart.view.*

class CartAdapter(val ctx: Context, val list: List<ProductEntity>) :
    RecyclerView.Adapter<CartAdapter.viewHolder>() {

    class viewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val img = itemview.img
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
    }
}