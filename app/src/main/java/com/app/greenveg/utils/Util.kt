package com.app.greenveg.utils

import android.content.Context
import android.os.Message
import android.widget.Toast

fun Context.toast(messag:String){
    Toast.makeText(this,messag,Toast.LENGTH_SHORT).show()
}