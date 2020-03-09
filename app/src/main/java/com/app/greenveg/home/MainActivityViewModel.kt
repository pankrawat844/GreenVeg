package com.app.greenveg.home

import androidx.lifecycle.ViewModel
import com.app.greenveg.model.Category
import com.app.greenveg.repo.Repository
import com.app.greenveg.utils.ApiException
import com.app.greenveg.utils.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(val repository: Repository) : ViewModel()
{
        var homeListener:HomeListener?=null
        fun getAllCategory(){
               CoroutineScope(Dispatchers.IO).launch {
                     try {


                         val response = repository.getCatList().enqueue(object :Callback<Category>{
                             override fun onFailure(call: Call<Category>, t: Throwable) {

                             }

                             override fun onResponse(
                                 call: Call<Category>,
                                 response: Response<Category>
                             ) {
                                 response.body()?.response.let {
                                     homeListener?.onSuccess(it!!)
                                     return
                                 }
                                 homeListener?.onFailour(response?.body()?.message!!)
                             }

                         })

                     }catch (e:ApiException)
                     {
                        homeListener?.onFailour(e.message!!)
                     }catch (e:NoInternetException)
                     {
                         homeListener?.onFailour(e.message!!)
                     }
               }
}
}