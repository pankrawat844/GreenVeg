package com.app.greenveg.repo

import com.app.greenveg.SafeApiRequest
import com.app.greenveg.model.Category
import com.app.greenveg.model.ProductList
import com.app.greenveg.utils.MyApi
import retrofit2.Call

class Repository(val myApi: MyApi):SafeApiRequest() {

    suspend fun getCatList(): Call<Category> {
        return myApi.getAllCat()
    }

    suspend fun getProductList(cat: Int): Call<ProductList> {
        return myApi.getProductList(cat)
    }

    suspend fun getSearchProductList(search: String): Call<ProductList> {
        return myApi.getSearchProductList(search)
    }
}