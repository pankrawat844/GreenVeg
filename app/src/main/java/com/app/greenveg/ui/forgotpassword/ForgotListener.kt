package com.app.greenveg.ui.forgotpassword

interface ForgotListener {
    fun onStarted()
    fun onSuccess(user: String)
    fun onFailure(msg: String)
}