package com.app.greenveg.fragment.productlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.greenveg.R
import com.app.greenveg.model.ProductList
import com.app.greenveg.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(private val context:Context,private val list:List<ProductList.Response>):RecyclerView.Adapter<ProductAdapter.Viewholder>()  {

    class Viewholder(view:View):RecyclerView.ViewHolder(view)
    {
        val product_name=view.product_name
        val cut_price=view.txtcutprice
        val price=view.txtprice
        val product_img=view.product_img
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
       return Viewholder(LayoutInflater.from(context).inflate(R.layout.item_product,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        Picasso.get().load(Constants.imageUrl+list.get(position).productImage1).placeholder(R.drawable.loader).fit().into(holder.product_img)
        holder.product_name.text=list.get(position).productName
        holder.cut_price.text=list.get(position).marketPrice
        holder.price.text=list.get(position).sellingPrice

    }
}