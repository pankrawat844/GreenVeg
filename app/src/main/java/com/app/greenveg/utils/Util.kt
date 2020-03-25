package com.app.greenveg.utils

import android.content.Context
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast


fun Context.toast(messag:String) {

//   Toast.makeText(this,messag,Toast.LENGTH_SHORT).show()
    val toast: Toast = Toast.makeText(this, messag, Toast.LENGTH_LONG)
    val toastLayout = toast.view as LinearLayout
    val toastTV = toastLayout.getChildAt(0) as TextView
    toastTV.setTextSize(
        TypedValue.COMPLEX_UNIT_PX,
        resources.getDimension(com.app.greenveg.R.dimen.toast)
    )
    toast.show()

}