package com.app.greenveg

import android.app.Application
import com.app.greenveg.fragment.productlist.ProductListModelFactory
import com.app.greenveg.fragment.productlist.ProductListViewModel
import com.app.greenveg.home.HomeViewModelFactory
import com.app.greenveg.home.MainActivityViewModel
import com.app.greenveg.repo.Repository
import com.app.greenveg.utils.MyApi
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MainApplication:Application(),KodeinAware {
    override val kodein= Kodein.lazy {
        bind() from singleton { MyApi() }
        bind() from singleton { Repository(instance()) }
        bind() from singleton { MainActivityViewModel(instance()) }
        bind() from singleton { HomeViewModelFactory(instance()) }
        bind() from singleton {
            ProductListModelFactory(
                instance()
            )
        }
        bind() from singleton {
            ProductListViewModel(
                instance()
            )
        }
    }

}