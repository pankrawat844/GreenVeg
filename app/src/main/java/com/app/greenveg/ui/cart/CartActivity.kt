package com.app.greenveg.ui.cart

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.greenveg.R
import com.app.greenveg.db.AppDatabase
import com.app.greenveg.ui.login.LoginActivity
import com.app.greenveg.utils.toast
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class CartActivity : AppCompatActivity() {
    var total: Float? = 0.00f

    var adapter: CartAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        CoroutineScope(Dispatchers.IO).launch {
            val cart = AppDatabase(this@CartActivity).cartDao().getCartProduct()
            var itemTotal: Int? = AppDatabase(this@CartActivity).cartDao().getCartProduct().size
            withContext(Dispatchers.Main) {
                for (i in cart) {
                    total =
                            total!! + i.sellingPrice?.toInt()?.times(i.selected_quantity.toFloat())!!

                }
                cart_total.text = total!!.toString()
                item_total.text = itemTotal.toString()
                cart_recylerview.layoutManager = LinearLayoutManager(this@CartActivity)
                adapter = CartAdapter(this@CartActivity, cart)
                cart_recylerview.adapter = adapter

            }
        }
        val mcurrentDate: Calendar = Calendar.getInstance()
        val mYear: Int = mcurrentDate.get(Calendar.YEAR)
        val mMonth: Int = mcurrentDate.get(Calendar.MONTH)
        val mDay: Int = mcurrentDate.get(Calendar.DAY_OF_MONTH)
        delivery_date.text = (mDay + 1).toString() + "/" + (mMonth + 1).toString() + "/" + mYear

        delivery_date.setOnClickListener {
            val mcurrentDate: Calendar = Calendar.getInstance()
            val mYear: Int = mcurrentDate.get(Calendar.YEAR)
            val mMonth: Int = mcurrentDate.get(Calendar.MONTH)
            val mDay: Int = mcurrentDate.get(Calendar.DAY_OF_MONTH)

            val mDatePicker = DatePickerDialog(
                this@CartActivity,
                OnDateSetListener { datepicker, selectedyear, selectedmonth, selectedday ->
                    mcurrentDate.set(Calendar.YEAR, selectedyear)
                    mcurrentDate.set(Calendar.MONTH, selectedmonth)
                    mcurrentDate.set(
                        Calendar.DAY_OF_MONTH,
                        selectedday
                    )

                    val sdf = SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.US
                    )
                    delivery_date.text = sdf.format(
                        mcurrentDate
                            .time
                    )

                }, mYear, mMonth, mDay

            )


            mDatePicker.setTitle(
                "Select Date"
            )
            val mcurrentDate1: Calendar = Calendar.getInstance()
            val mindate: Calendar = Calendar.getInstance()
            mindate.add(Calendar.DAY_OF_MONTH, 1)
            mcurrentDate1.add(Calendar.DAY_OF_MONTH, 5)
            mDatePicker.datePicker.minDate = mindate.timeInMillis
            mDatePicker.datePicker.maxDate = mcurrentDate1.timeInMillis
            mDatePicker.show()
        }
        back.setOnClickListener {
            onBackPressed()
        }

        next.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                var itemTotal: Int? =
                    AppDatabase(this@CartActivity).cartDao().getCartProduct().size

                if (itemTotal!! > 0) {
                    withContext(Dispatchers.Main) {
                        val alartDialog = AlertDialog.Builder(this@CartActivity)
                        alartDialog.setTitle("Confirm Order")
                        alartDialog.setMessage("Are You Sure To Confirm This Order.")
                        alartDialog.setPositiveButton(
                            "Yes"
                        ) { dialog, which ->
                            val sharedPef = getSharedPreferences("greenveg", Context.MODE_PRIVATE)
                            if (!sharedPef.getBoolean("islogin", false)) {
                                Intent(this@CartActivity, LoginActivity::class.java).also {
                                    startActivity(it)
                                }
                                dialog.dismiss()
                            } else {
                                Intent(this@CartActivity, LoginActivity::class.java).also {
                                    startActivity(it)
                                }
                                dialog.dismiss()
                            }
                        }

                        alartDialog.setNegativeButton(
                            "No"
                        ) { dialog, which ->
                            dialog.dismiss()
                        }

                        alartDialog.create().show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        toast("Please add a item to basket for order.")
                    }
                }
            }


        }
    }

    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "update_cart") {
                CoroutineScope(Dispatchers.IO).launch {
                    val cart = AppDatabase(this@CartActivity).cartDao().getCartProduct()
                    withContext(Dispatchers.Main) {
                        cart_recylerview.layoutManager = LinearLayoutManager(this@CartActivity)
                        cart_recylerview.adapter = CartAdapter(this@CartActivity, cart)
                        item_total.text =
                            AppDatabase(this@CartActivity).cartDao().getCartProduct().size
                                .toString()

                    }
                }
            } else if (intent?.action == "change_quantity") {
                if (cart_recylerview.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                    total = 0.00f
                    CoroutineScope(Dispatchers.IO).launch {
                        val cart = AppDatabase(this@CartActivity).cartDao().getCartProduct()
                        var itemTotal: Int? =
                                AppDatabase(this@CartActivity).cartDao().getCartProduct().size

                        withContext(Dispatchers.Main) {
                            for (i in cart) {
                                total = total!! + i.sellingPrice?.toInt()
                                        ?.times(i.selected_quantity.toFloat())!!
                            }
                            cart_total.text = total!!.toString()
                            item_total.text = itemTotal.toString()

                            cart_recylerview.layoutManager = LinearLayoutManager(this@CartActivity)
                            cart_recylerview.adapter = CartAdapter(this@CartActivity, cart)
//                            adapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter("update_cart"))
        registerReceiver(broadcastReceiver, IntentFilter("change_quantity"))

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}