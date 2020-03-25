package com.app.greenveg.ui.cart

interface CartListener {
    fun onStarted()
    fun onSuccess(user: String)
    fun onFailure(msg: String)
}