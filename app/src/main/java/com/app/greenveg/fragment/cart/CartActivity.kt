package com.app.greenveg.fragment.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.greenveg.R
import com.app.greenveg.db.AppDatabase
import com.app.greenveg.ui.search.SearchProductAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        CoroutineScope(Dispatchers.IO).launch {
            val cart = AppDatabase(this@CartActivity).cartDao().getCartProduct()
            withContext(Dispatchers.Main) {
                search_recylerview.layoutManager = LinearLayoutManager(this@CartActivity)
                search_recylerview.adapter = SearchProductAdapter(this@CartActivity, cart)
            }
        }
    }
}