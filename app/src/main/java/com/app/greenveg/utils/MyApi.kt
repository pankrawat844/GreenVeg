package com.app.greenveg.utils

import com.app.greenveg.model.Category
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MyApi {
companion object
{
    operator fun invoke():MyApi{
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl("https://schmanagement.000webhostapp.com/greenveg/api/")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MyApi::class.java)
    }
}

    @GET("category_list.php")
    fun getAllCat():Call<Category>
}