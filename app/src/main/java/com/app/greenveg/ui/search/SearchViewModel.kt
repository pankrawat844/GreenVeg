package com.app.greenveg.ui.search

import androidx.lifecycle.ViewModel
import com.app.greenveg.model.ProductList
import com.app.greenveg.repo.Repository
import com.app.greenveg.ui.productlist.ProductLisetner
import com.app.greenveg.utils.ApiException
import com.app.greenveg.utils.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(val repository: Repository) : ViewModel() {
    var homeListener: ProductLisetner? = null
    fun getSearchList(search: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                homeListener?.onStarted()
                repository.getSearchProductList(search).enqueue(object : Callback<ProductList> {
                    override fun onFailure(call: Call<ProductList>, t: Throwable) {
                        homeListener?.onFailure(t.message!!)
                    }

                    override fun onResponse(
                        call: Call<ProductList>,
                        response: Response<ProductList>
                    ) {
                        response.body()?.let {
                            homeListener?.onSuccess(it)
                            return
                        }
                        homeListener?.onFailure(response.body()?.message!!)
                    }

                })

            } catch (e: ApiException) {
                homeListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                homeListener?.onFailure(e.message!!)
            }
        }
    }
}