package com.app.greenveg.ui.otp

import androidx.lifecycle.ViewModel
import com.app.greenveg.model.Signup
import com.app.greenveg.repo.Repository
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Response

class OtpViewmodel(val repository: Repository) : ViewModel() {
    var listener: OtpListener? = null
    fun getSignup(
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
    ) {
        listener?.onStarted()
        repository.signUP(
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
        ).enqueue(object : Callback<Signup> {
            override fun onFailure(call: retrofit2.Call<Signup>, t: Throwable) {
                listener?.onFailure(t.message!!)
            }

            override fun onResponse(call: retrofit2.Call<Signup>, response: Response<Signup>) {
                if (response.isSuccessful) {
//                    val jsonObject = JSONObject(response.body().toString())
                    val data = response.body()
                    if (data?.data?.success!!) {
                        listener?.onSuccess(data.data.msg!!)
                    } else {
                        listener?.onFailure(data.data.msg!!)
                    }
                } else {
                    listener?.onFailure(JSONObject(response.errorBody()?.string()).getString("msg"))
                }
            }

        })
    }
}