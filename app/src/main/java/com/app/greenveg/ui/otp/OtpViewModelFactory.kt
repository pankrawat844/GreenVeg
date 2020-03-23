package com.app.greenveg.ui.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.greenveg.repo.Repository

class OtpViewModelFactory(val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OtpViewmodel(repository) as T
    }
}