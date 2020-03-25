package com.app.greenveg.ui.cart

import androidx.lifecycle.ViewModel
import com.app.greenveg.model.Signup
import com.app.greenveg.repo.Repository
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewmodel(val repository: Repository) : ViewModel() {
    var cartListener: CartListener? = null
    fun getCart(jsonArray: JSONArray) {
        repository.addtoCart(jsonArray).enqueue(object : Callback<Signup> {
            override fun onFailure(call: Call<Signup>, t: Throwable) {
                cartListener?.onFailure(t.message!!)

            }

            override fun onResponse(call: Call<Signup>, response: Response<Signup>) {
                if (response.isSuccessful) {
                    if (response.body()?.data?.success!!) {
                        cartListener?.onSuccess(response.body()?.data!!.msg!!)
                    } else {
                        cartListener?.onFailure(response.body()?.data!!.msg!!)

                    }
                } else {
                    cartListener?.onFailure(JSONObject(response.errorBody()?.string()).getString("msg"))

                }
            }

        })
    }
}