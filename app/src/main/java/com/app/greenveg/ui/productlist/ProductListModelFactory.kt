package com.app.greenveg.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.greenveg.repo.Repository

class ProductListModelFactory(val repository: Repository) :ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductListViewModel(
            repository
        ) as T
    }
}