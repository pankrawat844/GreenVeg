package com.app.greenveg.ui.signup

import androidx.lifecycle.ViewModel
import com.app.greenveg.model.ServiceArea
import com.app.greenveg.repo.Repository
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewmodel(val repository: Repository) : ViewModel() {
    var signupListene: SignupListener? = null

    fun getServiceArea() {
        repository.getServiceArea().enqueue(object : Callback<ServiceArea> {
            override fun onFailure(call: Call<ServiceArea>, t: Throwable) {
                signupListene?.onFailure(t.message!!)
            }

            override fun onResponse(call: Call<ServiceArea>, response: Response<ServiceArea>) {
                if (response.isSuccessful) {
                    signupListene?.onServiceAreaSucces(response.body()!!)
                } else {
                    signupListene?.onFailure(
                        JSONObject(response.errorBody()?.string()!!).getString(
                            "message"
                        )
                    )
                }
            }

        })
    }
}