package com.app.greenveg.ui.forgotpassword

import android.view.View
import androidx.lifecycle.ViewModel
import com.app.greenveg.model.Signup
import com.app.greenveg.repo.Repository
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotViewmodel(val repository: Repository) : ViewModel() {
    var password: String? = null
    var confirmpassword: String? = null
    var mobile: String? = null
    var forgotListener: ForgotListener? = null
    fun changePassword(view: View) {
        forgotListener?.onStarted()
        if (password.isNullOrEmpty()) {
            forgotListener?.onFailure("Please enter new password")
            return
        }

        if (confirmpassword.isNullOrEmpty()) {
            forgotListener?.onFailure("Please enter confirm password")
            return
        }

        if (password != confirmpassword) {
            forgotListener?.onFailure("Password and confirm password is not match.")
            return
        }

        if (password?.length!! < 6 || password?.length!! > 10) {
//            Toast.makeText(view.context, "Please enter 10 digit Mobile No.", Toast.LENGTH_LONG).show()
            forgotListener?.onFailure("Password would be minimum 6 and maximum 10 character having at least one letter and one number.")
            return
        }
        if (!password!!.trim { it <= ' ' }
                .matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$".toRegex())) {
            forgotListener?.onFailure("Password would be minimum 6 and maximum 10 character having at least one letter and one number.")
            return
        }
        repository.changePassword(mobile, password).enqueue(object : Callback<Signup> {
            override fun onFailure(call: Call<Signup>, t: Throwable) {

            }

            override fun onResponse(call: Call<Signup>, response: Response<Signup>) {
                if (response.isSuccessful) {
                    if (response.body()?.data?.success!!) {
                        forgotListener?.onSuccess(response.body()?.data?.msg!!)
                    } else {
                        forgotListener?.onFailure(response.body()?.data?.msg!!)
                    }
                } else {
                    forgotListener?.onFailure(
                        JSONObject(
                            response.errorBody()?.string()!!
                        ).getString("msg")
                    )

                }
            }

        })
    }
}