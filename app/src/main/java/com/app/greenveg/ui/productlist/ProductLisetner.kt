package com.app.greenveg.ui.productlist

import com.app.greenveg.model.ProductList

interface ProductLisetner {
    fun onStarted()
    fun onSuccess(productList: ProductList)
    fun onFailure(msg: String)
}