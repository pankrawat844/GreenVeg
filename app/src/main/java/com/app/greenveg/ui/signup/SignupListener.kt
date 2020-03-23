package com.app.greenveg.ui.signup

import com.app.greenveg.model.ServiceArea
import com.app.greenveg.model.User

interface SignupListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(msg: String)
    fun onServiceAreaSucces(area: ServiceArea)
    fun onsecondViewVisble()
    fun onback()
}