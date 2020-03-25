package com.app.greenveg.ui.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.app.greenveg.R
import com.app.greenveg.databinding.ActivityForgotPassBinding
import com.app.greenveg.ui.login.LoginActivity
import com.app.greenveg.utils.toast
import kotlinx.android.synthetic.main.activity_forgot_pass.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ForgotPassActivity : AppCompatActivity(), ForgotListener, KodeinAware {
    val factory: ForgotPasswordModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding: ActivityForgotPassBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_forgot_pass)
        val viewmodel = ViewModelProviders.of(this, factory).get(ForgotViewmodel::class.java)
        viewmodel.mobile = intent.getStringExtra("mobile")
        databinding.data = viewmodel
        viewmodel.forgotListener = this
    }

    override fun onStarted() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(user: String) {
        progressBar.visibility = View.GONE
        toast(user)
        Intent(this, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

    }

    override fun onFailure(msg: String) {
        progressBar.visibility = View.GONE
        toast(msg)
    }

    override val kodein by kodein()
}