package com.app.greenveg.utils

import com.app.greenveg.model.*
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

    @GET("service_area.php")
    fun serviceArea(): Call<ServiceArea>

    @FormUrlEncoded
    @POST("signup_api.php")
    fun getSignup(
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("username") username: String?,
        @Field("servicearea") serviceArea: String?,
        @Field("mobile") mobile: String?,
        @Field("alternate_phone") alternatemobile: String?,
        @Field("address_line1") address1: String?,
        @Field("address_line2") address2: String?,
        @Field("address_line3") address3: String?,
        @Field("address_line4") address4: String?,
        @Field("address_line5") address5: String?,
        @Field("landmark") landmark: String?,
        @Field("pincode") pincode: String?,
        @Field("district") district: String?,
        @Field("state") state: String?
    ): Call<Signup>

}