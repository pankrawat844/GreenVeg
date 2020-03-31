package com.app.greenveg.repo

import com.app.greenveg.SafeApiRequest
import com.app.greenveg.model.*
import com.app.greenveg.ui.history.HistoryData
import com.app.greenveg.ui.history.HistoryDetailItem
import com.app.greenveg.utils.MyApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
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


    fun updateProfile(
        userId: String,
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
        return myApi.updateProfile(
            userId,
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

    fun changePassword(mobile: String?, password: String?): Call<Signup> {
        return myApi.changePassord(mobile!!, password!!)
    }

    fun checkmobile(mobile: String?): Call<Signup> {
        return myApi.checkuser(mobile)
    }

    fun addtoCart(data: JSONArray?): Call<Signup> {
        val body: RequestBody = data.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return myApi.addtoCart(body)
    }

    fun getHistory(userId: String): Call<HistoryData> {
        return myApi.history(userId)
    }

    fun getHistoryDetail(orderId: String): Call<HistoryDetailItem> {
        return myApi.historyDetail(orderId)
    }

    fun cancelOrder(orderId: String): Call<Signup> {
        return myApi.cancelOrder(orderId)
    }
}