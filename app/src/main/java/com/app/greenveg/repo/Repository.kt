package com.app.greenveg.repo

import com.app.greenveg.SafeApiRequest
import com.app.greenveg.model.*
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

    fun getUserLogin(email: String, password: String): Call<User> {
        return myApi.getUser(email, password)
    }

    fun getServiceArea(): Call<ServiceArea> {
        return myApi.serviceArea()
    }

    fun signUP(
        name: String?,
        email: String?,
        password: String?,
        username: String?,
        serviceArea: String?,
        mobile: String?,
        alternatemobile: String?,
        address1: String?,
        address2: String?,
        address3: String?,
        address4: String?,
        address5: String?,
        landmark: String?,
        pincode: String?,
        district: String?,
        state: String?
    ): Call<Signup> {
        return myApi.getSignup(
            name,
            email,
            password,
            username,
            serviceArea,
            mobile,
            alternatemobile,
            address1,
            address2,
            address3,
            address4,
            address5,
            landmark,
            pincode,
            district,
            state
        )
    }
}