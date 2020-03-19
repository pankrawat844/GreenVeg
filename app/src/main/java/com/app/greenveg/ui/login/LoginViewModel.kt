package com.app.greenveg.ui.login

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.app.greenveg.model.User
import com.app.greenveg.repo.Repository
import com.app.greenveg.ui.signup.SignupActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(val repository: Repository) : ViewModel() {
    var loginListener: LoginListener? = null
    var email: String? = null
    var password: String? = null
    fun getLogin(view: View) {
        if (email.isNullOrEmpty()) {
            loginListener?.onFailure("Please enter email id.")
            return
        }
        if (password.isNullOrEmpty()) {
            loginListener?.onFailure("Please enter password.")
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            repository.getUserLogin(email!!, password!!).enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    loginListener?.onFailure(t.message!!)
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        if (response.body()?.data?.success!!) {
                            loginListener?.onSuccess(response.body()!!)
                        } else {
                            loginListener?.onFailure(response.body()?.data?.msg!!)
                        }
                    } else {

                    }
                }

            })
        }
    }

    fun getSignup(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
}