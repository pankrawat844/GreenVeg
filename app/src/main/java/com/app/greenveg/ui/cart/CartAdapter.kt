package com.app.greenveg.ui.cart

import android.annotation.SuppressLint
import android.app.AlertDialog
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
        val product_total = itemview.total
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_cart, parent, false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var old_Quantity = ""
        holder.setIsRecyclable(false)
        val product = list[position]
        Picasso.get().load(Constants.imageUrl + product.productImage4).into(holder.img)
        holder.product_name.text = product.productName
        if (product.unitOfMeasure == "KG") {
            holder.product_price.text = "Unit Price: Rs " + product.sellingPrice + " per kg"
        } else if (product.unitOfMeasure == "BUNDLE") {
            holder.product_price.text = "Unit Price: Rs " + product.sellingPrice + " per bundle"

        } else {
            holder.product_price.text = "Unit Price: Rs " + product.sellingPrice + " per number"

        }

        holder.delete.setOnClickListener {
            val alartDialog = AlertDialog.Builder(ctx)
            alartDialog.setTitle("Delete Product")
            alartDialog.setMessage("Are You Sure To Remove Product From Basket.")
            alartDialog.setPositiveButton(
                "Yes"
            ) { dialog, which ->
                CoroutineScope(Dispatchers.IO).launch {
                    AppDatabase(ctx).cartDao().deleteFromCart(product)
                    withContext(Dispatchers.Main) {
                        ctx.toast("${list[position].productName} removed from basket")
                        ctx.sendBroadcast(Intent("change_quantity"))
                    }
                }
                dialog.dismiss()
            }

            alartDialog.setNegativeButton(
                "No"
            ) { dialog, which ->
                dialog.dismiss()
            }

            alartDialog.create().show()

        }
        if (product.unitOfMeasure == "KG") {
            holder.quantity.adapter = ArrayAdapter(
                ctx,
                R.layout.spinner_item,
                ctx.resources.getStringArray(R.array.kg_array)
            )
            for ((i, value) in ctx.resources.getStringArray(R.array.kg_array).withIndex()) {

                if (value.contains(product.selected_quantity_txt.toString())) {
                    holder.quantity.setSelection(i, false)
                }
            }
        } else {
            holder.quantity.adapter = ArrayAdapter(
                ctx,
                R.layout.spinner_item,
                ctx.resources.getStringArray(R.array.kg_bundle)
            )
            for ((i, value) in ctx.resources.getStringArray(R.array.kg_bundle).withIndex()) {

                if (value.contains(product.selected_quantity_txt.toString())) {
                    holder.quantity.setSelection(i, false)
                }
            }
        }


        holder.product_total.text = "Product Total: " + (product.sellingPrice?.toFloat()
            ?.times(product.selected_quantity)).toString()


        holder.quantity
            .setOnItemSelectedEvenIfUnchangedListener(object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
//                product.selected_quantity=parent?.getItemAtPosition(position).toString().toFloat()

                    if (product.unitOfMeasure == "KG") {
                        if (holder.quantity.selectedItem.toString() == "250g") {
                            product.selected_quantity = 0.25f
                        } else if (holder.quantity.selectedItem.toString() == "500g") {
                            product.selected_quantity = 0.50f
                        } else if (holder.quantity.selectedItem.toString() == "750g") {
                            product.selected_quantity = 0.75f
                        } else if (holder.quantity.selectedItem.toString() == "1kg") {
                            product.selected_quantity = 1.00f
                        } else if (holder.quantity.selectedItem.toString() == "1.25kg") {
                            product.selected_quantity = 1.25f
                        } else if (holder.quantity.selectedItem.toString() == "1.5kg") {
                            product.selected_quantity = 1.50f
                        } else if (holder.quantity.selectedItem.toString() == "1.75kg") {
                            product.selected_quantity = 1.75f
                        } else if (holder.quantity.selectedItem.toString() == "2kg") {
                            product.selected_quantity = 2.00f
                        } else if (holder.quantity.selectedItem.toString() == "2.25kg") {
                            product.selected_quantity = 2.25f
                        } else if (holder.quantity.selectedItem.toString() == "2.5kg") {
                            product.selected_quantity = 2.50f
                        } else if (holder.quantity.selectedItem.toString() == "2.75kg") {
                            product.selected_quantity = 2.75f
                        } else if (holder.quantity.selectedItem.toString() == "3kg") {
                            product.selected_quantity = 3.00f
                        }
                    } else {
//                        product.selected_quantity =
//                                holder.quantity.selectedItem.toString().toFloat()
                        if (holder.quantity.selectedItem.toString() == "1") {
                            product.selected_quantity = 1f
                        } else if (holder.quantity.selectedItem.toString() == "2") {
                            product.selected_quantity = 2f
                        } else if (holder.quantity.selectedItem.toString() == "3") {
                            product.selected_quantity = 3f
                        } else if (holder.quantity.selectedItem.toString() == "4") {
                            product.selected_quantity = 4f
                        } else if (holder.quantity.selectedItem.toString() == "5") {
                            product.selected_quantity = 5f
                        } else if (holder.quantity.selectedItem.toString() == "6") {
                            product.selected_quantity = 6f
                        } else if (holder.quantity.selectedItem.toString() == "7") {
                            product.selected_quantity = 7f
                        } else if (holder.quantity.selectedItem.toString() == "8") {
                            product.selected_quantity = 8f
                        } else if (holder.quantity.selectedItem.toString() == "9") {
                            product.selected_quantity = 9f
                        } else if (holder.quantity.selectedItem.toString() == "10") {
                            product.selected_quantity = 10f
                        }
                    }
                    product.selected_quantity_txt = holder.quantity.selectedItem.toString()
//                    if (old_Quantity != parent?.getItemAtPosition(position).toString()) {
//                        old_Quantity = holder.quantity.selectedItem.toString()
                    CoroutineScope(Dispatchers.IO).launch {
                        AppDatabase(ctx).cartDao().updateCart(product)
                    }
                    val intent = Intent("change_quantity").also {
                        it.putExtra("position", list.indexOf(product))
                    }
                    ctx.sendBroadcast(intent)

                }

            })
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}