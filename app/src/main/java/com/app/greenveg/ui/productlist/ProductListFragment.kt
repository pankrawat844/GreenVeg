package com.app.greenveg.ui.productlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.greenveg.R
import com.app.greenveg.model.ProductList
import com.app.greenveg.utils.toast
import kotlinx.android.synthetic.main.product_list_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProductListFragment : Fragment(),
    ProductLisetner,KodeinAware {
    val factory: ProductListModelFactory by instance()
    companion object {
        fun newInstance() =
            ProductListFragment()
    }

    private lateinit var viewModel: ProductListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,factory).get(ProductListViewModel::class.java)
        viewModel.productLisetner=this
        viewModel.getProductList((arguments?.getString("cat_id","")!!).toInt())

    }

    override fun onStarted() {

    }

    override fun onSuccess(productList: ProductList) {
        Log.e("response",productList.toString())
        product_recylerview.layoutManager=LinearLayoutManager(activity)
        product_recylerview.adapter=ProductAdapter(activity!!,productList.response!!)
    }

    override fun onFailure(msg: String) {
        context?.toast(msg)
    }

    override val kodein by kodein()
}