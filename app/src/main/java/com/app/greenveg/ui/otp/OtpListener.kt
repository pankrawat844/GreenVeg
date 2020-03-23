package com.app.greenveg.ui.otp

interface OtpListener {
    fun onStarted()
    fun onSuccess(user: String)
    fun onFailure(msg: String)
}