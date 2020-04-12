package com.app.greenveg.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.app.greenveg.NavigationActivity
import com.app.greenveg.R
import com.app.greenveg.databinding.ActivityLoginBinding
import com.app.greenveg.model.User
import com.app.greenveg.ui.otp.OtpActivity
import com.app.greenveg.utils.toast
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), LoginListener, KodeinAware {
    val factory: LoginViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        viewModel.loginListener = this
        databinding.data = viewModel
    }

    override fun onStarted() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(user: User) {
        progressBar.visibility = View.GONE
        if (user.data.response.userStatus == "1") {
            toast("Login Successfull. Now place order through basket.")
            val sharedPreferences = getSharedPreferences("greenveg", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit {
                putBoolean("islogin", true)
                putString("userid", user.data.response.userid)
                putString("name", user.data.response.name)
                putString("username", user.data.response.userName)
                putString("email", user.data.response.email)
                putString("mobile", user.data.response.mobile)
                putString("password", user.data.response.password)
                putString("servicearea", user.data.response.serviceAvailArea)
                putString("alternatemobile", user.data.response.alternatePhone)
                putString("address1", user.data.response.addressLine1)
                putString("address2", user.data.response.addressLine2)
                putString("address3", user.data.response.addressLine3)
                putString("address4", user.data.response.addressLine4)
                putString("address5", user.data.response.addressLine5)
                putString("landmark", user.data.response.landmark)
                putString("pincode", user.data.response.pin)
                putString("district", user.data.response.district)
                putString("state", user.data.response.state)
                commit()
            }

            Intent(this, NavigationActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        } else {
            toast("Currently you are disabled. Please contact our support team.")
        }
    }

    override fun onCheckUserSuccess(mobile: String) {
        progressBar.visibility = View.GONE

        Intent(this@LoginActivity, OtpActivity::class.java).also {
            it.putExtra("activity", "login")
            it.putExtra("mobile", mobile)
            startActivity(it)
        }
    }

    override fun onFailure(msg: String) {
        progressBar.visibility = View.GONE
        toast(msg)
    }

    override val kodein by kodein()
}