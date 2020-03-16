package com.app.greenveg.ui.home

import com.app.greenveg.model.Category

interface HomeListener
{
    fun onStarted()
    fun onSuccess(response:List<Category.Response>)
    fun onFailour(msg:String)

}
