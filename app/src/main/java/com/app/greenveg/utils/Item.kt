package com.app.greenveg.utils

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable


class Item : BaseObservable() {
    @get:Bindable
    var selectedItemPosition = 0
        set(selectedItemPosition) {
            field = selectedItemPosition
            notifyPropertyChanged(selectedItemPosition)
        }

}