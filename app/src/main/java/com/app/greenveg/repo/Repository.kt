package com.app.greenveg.repo

import com.app.greenveg.SafeApiRequest
import com.app.greenveg.model.Category
import com.app.greenveg.utils.MyApi
import retrofit2.Call
import retrofit2.Response

class Repository(val myApi: MyApi):SafeApiRequest() {

    suspend fun getCatList():Call<Category>
    {
        return myApi.getAllCat()
    }
}