package com.app.greenveg.ui.signup

import com.app.greenveg.model.ServiceArea

interface SignupListener {
    fun onStarted()
    fun onSuccess(user: String)
    fun onFailure(msg: String)
    fun onServiceAreaSucces(area: ServiceArea)
    fun onsecondViewVisble()
    fun onback()
}