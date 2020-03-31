package com.app.greenveg.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.greenveg.repo.Repository

class HistoryViewModelFactory(val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HistoryViewmodel(repository) as T
    }
}