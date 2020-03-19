package com.app.greenveg.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.app.greenveg.R
import com.app.greenveg.databinding.ActivityLoginBinding
import com.app.greenveg.model.User
import com.app.greenveg.utils.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), LoginListener, KodeinAware {
    val factory: LoginViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        viewModel.loginListener = this
        databinding.data = viewModel
    }

    override fun onStarted() {

    }

    override fun onSuccess(user: User) {
        toast("Login Successfull")
    }

    override fun onFailure(msg: String) {
        toast(msg)
    }

    override val kodein by kodein()
}