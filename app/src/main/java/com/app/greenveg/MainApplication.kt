package com.app.greenveg

import android.app.Application
import com.app.greenveg.repo.Repository
import com.app.greenveg.ui.cart.CartViewmodel
import com.app.greenveg.ui.cart.CartViewmodelFactory
import com.app.greenveg.ui.forgotpassword.ForgotPasswordModelFactory
import com.app.greenveg.ui.forgotpassword.ForgotViewmodel
import com.app.greenveg.ui.history.HistoryActivity
import com.app.greenveg.ui.history.HistoryViewModelFactory
import com.app.greenveg.ui.history.HistoryViewmodel
import com.app.greenveg.ui.home.HomeFragment
import com.app.greenveg.ui.home.HomeViewModelFactory
import com.app.greenveg.ui.home.MainActivityViewModel
import com.app.greenveg.ui.login.LoginViewModel
import com.app.greenveg.ui.login.LoginViewModelFactory
import com.app.greenveg.ui.otp.OtpViewModelFactory
import com.app.greenveg.ui.otp.OtpViewmodel
import com.app.greenveg.ui.productlist.ProductListModelFactory
import com.app.greenveg.ui.productlist.ProductListViewModel
import com.app.greenveg.ui.profile.ProfileViewModel
import com.app.greenveg.ui.profile.ProfileViewModelFactory
import com.app.greenveg.ui.search.SearchViewModel
import com.app.greenveg.ui.search.SearchViewModelFactory
import com.app.greenveg.ui.signup.SignupViewmodel
import com.app.greenveg.ui.signup.SignupViewmodelFactory
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
        bind() from singleton {
            MainActivityViewModel(
                instance()
            )
        }
        bind() from singleton {
            HomeViewModelFactory(
                instance()
            )
        }
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

        bind() from singleton { HomeFragment() }
        bind() from singleton { SearchViewModel(instance()) }
        bind() from singleton { SearchViewModelFactory(instance()) }
        bind() from singleton { LoginViewModel(instance()) }
        bind() from singleton { LoginViewModelFactory(instance()) }
        bind() from singleton { SignupViewmodel(instance()) }
        bind() from singleton { SignupViewmodelFactory(instance()) }
        bind() from singleton { OtpViewmodel(instance()) }
        bind() from singleton { OtpViewModelFactory(instance()) }
        bind() from singleton { ForgotPasswordModelFactory(instance()) }
        bind() from singleton { ForgotViewmodel(instance()) }
        bind() from singleton { CartViewmodel(instance()) }
        bind() from singleton { CartViewmodelFactory(instance()) }
        bind() from singleton { HistoryViewmodel(instance()) }
        bind() from singleton { HistoryActivity() }
        bind() from singleton { HistoryViewModelFactory(instance()) }
        bind() from singleton { ProfileViewModel(instance()) }
        bind() from singleton { ProfileViewModelFactory(instance()) }
    }

}