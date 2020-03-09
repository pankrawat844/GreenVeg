package com.app.greenveg.home

import com.app.greenveg.model.Category

interface HomeListener
{
    fun onSuccess(response:List<Category.Response>)
    fun onFailour(msg:String)

}
