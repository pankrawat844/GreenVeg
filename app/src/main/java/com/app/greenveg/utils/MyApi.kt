package com.app.greenveg.utils

import com.app.greenveg.model.Category
import com.app.greenveg.model.ProductList
import com.app.greenveg.model.User
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

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
    fun getAllCat(): Call<Category>

    @FormUrlEncoded
    @POST("product_list.php")
    fun getProductList(
            @Field("category_id") cat_id: Int
    ): Call<ProductList>

    @FormUrlEncoded
    @POST("search_product_list.php")
    fun getSearchProductList(
            @Field("search") cat_id: String
    ): Call<ProductList>

    @FormUrlEncoded
    @POST("login_api.php")
    fun getUser(
            @Field("email") email: String,
            @Field("password") password: String
    ): Call<User>
}