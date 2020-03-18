package com.app.greenveg.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    fun getLogin(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            
        }
    }
}