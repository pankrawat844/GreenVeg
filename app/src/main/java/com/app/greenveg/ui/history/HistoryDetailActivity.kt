package com.app.greenveg.ui.history

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.greenveg.NavigationActivity
import com.app.greenveg.R
import com.app.greenveg.db.AppDatabase
import com.app.greenveg.model.Signup
import com.app.greenveg.ui.cart.CartActivity
import com.app.greenveg.utils.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_history_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

class HistoryDetailActivity : AppCompatActivity(), KodeinAware, HistoryListener {
    val factory: HistoryViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_detail)
        val viewmodel: HistoryViewmodel =
            ViewModelProviders.of(this, factory).get(HistoryViewmodel::class.java)
        viewmodel.historyListener = this
        viewmodel.getHistoryDetail(intent.getStringExtra("orderid")!!)
        back.setOnClickListener {
            onBackPressed()
        }

        CoroutineScope(Dispatchers.IO).launch {

            val size = AppDatabase(this@HistoryDetailActivity).cartDao().getCartProduct().size
            withContext(Dispatchers.Main) {
                cart_item.text = size.toString()
            }
        }

        button1.setOnClickListener {
            Intent(this, CartActivity::class.java).also {
                startActivity(it)
            }
        }
        cancelOrder.setOnClickListener {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = simpleDateFormat.format(Calendar.getInstance().time)
            if (deliveryDate.text.toString().equals(date)) {
                toast("You cannot cancelled order which delivery date is today. ")
            } else {
                val alertDialog = AlertDialog.Builder(this@HistoryDetailActivity)
                alertDialog.setTitle("Cancel Order")
                alertDialog.setMessage("Are you sure to cancel this order.")
                alertDialog.setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, which ->

                        dialog.dismiss()

                        viewmodel.cancelOrder(orderId.text.toString())

                    })
                alertDialog.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                alertDialog.create().show()
            }
        }
        home.setOnClickListener {
            Intent(this@HistoryDetailActivity, NavigationActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(it)


            }
        }
    }

    override val kodein by kodein()
    override fun onStarted() {
        progressBar.visibility = View.VISIBLE

    }

    override fun onSuccess(response: HistoryData) {

    }

    @SuppressLint("SetTextI18n")
    override fun onSuccessDetail(response: HistoryDetailItem) {
        progressBar.visibility = View.GONE
        val data = response.data.response[0]
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val changeDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val orderdate = dateFormat.parse(data.orderDate)
        val deliverydate = dateFormat.parse(data.deliveryDate)
        orderDate.text = changeDateFormat.format(orderdate!!)
        deliveryDate.text = changeDateFormat.format(deliverydate!!)
//        address_line1.text=data.addressLine1
//        address_line2.text=data.addressLine2
//        address_line3.text=data.addressLine3
//        address_line4.text=data.addressLine4
//        address_line5.text=data.addressLine5
        orderId.text = data.orderId
        noItem.text = response.data.response.size.toString()
//        serviceArea.text=data.serviceArea
//        landmark.text=data.landmark
//        district.text=data.district
//        state.text=data.state
//        pincode.text=data.pincode
        orderTotal.text = "Rs. " + data.orderTotal
        status.text = data.orderStatus
        if (data.orderStatus.equals("active", false)) {
            cancelOrder.visibility = View.VISIBLE

        } else {
            status.setTextColor(Color.RED)
        }
        val adapter = GroupAdapter<GroupieViewHolder>().apply {

            addAll(response.data.response.changeAdapter())
        }
        detailRecylerview.layoutManager = LinearLayoutManager(this)
        detailRecylerview.adapter = adapter
    }

    override fun onSuccessCancel(response: Signup) {
        progressBar.visibility = View.GONE
        if (response.data?.success!!) {
            toast(response.data.msg!!)
            cancelOrder.visibility = View.GONE
            status.text = "cancelled"
            status.setTextColor(Color.RED)
        }
    }

    override fun onFailour(msg: String) {
        progressBar.visibility = View.GONE
        toast(msg)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        Intent(this, NavigationActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            it.putExtra("from_cart", true)
            startActivity(it)

        }
    }

    private fun List<HistoryDetailItem.Data.Response>.changeAdapter(): List<HistoryDetailAdapter> {
        return this.map {
            HistoryDetailAdapter(it)
        }

    }
}