package com.app.greenveg.utils

import android.content.Context
import android.util.AttributeSet


class MySpinner(context: Context?, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatSpinner(context, attrs) {
    var listener: OnItemSelectedListener? = null
    override fun setSelection(position: Int) {
        super.setSelection(position)
        if (listener != null) listener!!.onItemSelected(null, null, position, 0)
    }

    fun setOnItemSelectedEvenIfUnchangedListener(
        listener: OnItemSelectedListener?
    ) {
        this.listener = listener
    }
}