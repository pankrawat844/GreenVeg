package com.app.greenveg.ui.login

import com.app.greenveg.model.User

interface LoginListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onCheckUserSuccess(mobile: String)
    fun onFailure(msg: String)
}