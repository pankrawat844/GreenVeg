package com.app.greenveg.ui.search

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.greenveg.R
import com.app.greenveg.db.AppDatabase
import com.app.greenveg.model.ProductList
import com.app.greenveg.ui.productlist.ProductLisetner
import com.app.greenveg.utils.toast
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SearchActivity : AppCompatActivity(), KodeinAware, ProductLisetner {
    val factory: SearchViewModelFactory by instance()
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
        viewModel.homeListener = this
        viewModel.getSearchList(intent.getStringExtra("search"))
        back.setOnClickListener {
            onBackPressed()
        }

        CoroutineScope(Dispatchers.IO).launch {

            val size = AppDatabase(this@SearchActivity).cartDao().getCartProduct().size
            withContext(Dispatchers.Main) {
                cart_item.text = size.toString()
            }
        }
    }

    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {

            when (intent?.action) {
                "change_value_search" -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        val size = AppDatabase(this@SearchActivity).cartDao().getCartProduct().size
                        withContext(Dispatchers.Main) {
                            cart_item.text = size.toString()
                        }
                    }

                }


            }
        }
    }

    override val kodein: Kodein by kodein()
    override fun onStarted() {

    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadCastReceiver, IntentFilter("change_value_search"))

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadCastReceiver)
    }

    override fun onSuccess(productList: ProductList) {
        Log.e("response", productList.toString())
        search_recylerview.layoutManager = LinearLayoutManager(this)
        search_recylerview.adapter = SearchProductAdapter(this, productList.response!!)
    }

    override fun onFailure(msg: String) {
        toast(msg)
    }
}