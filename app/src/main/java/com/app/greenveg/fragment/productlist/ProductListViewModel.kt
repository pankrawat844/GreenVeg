package com.app.greenveg.fragment.productlist

import androidx.lifecycle.ViewModel
import com.app.greenveg.model.ProductList
import com.app.greenveg.repo.Repository
import com.app.greenveg.utils.ApiException
import com.app.greenveg.utils.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListViewModel(val repository: Repository) : ViewModel() {
    var productLisetner: ProductLisetner?=null
fun getProductList(cat:Int){
    CoroutineScope(Dispatchers.Main).launch {
        try {

            repository.getProductList(cat).enqueue(object :
                Callback<ProductList> {
                override fun onFailure(call: Call<ProductList>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<ProductList>,
                    response: Response<ProductList>
                ) {
                    response.body()?.let {
                        productLisetner?.onSuccess(it!!)
                        return
                    }
                      productLisetner?.onFailure(response?.body()?.message!!)
                }

            })

        }catch (e: ApiException)
        {
            productLisetner?.onFailure(e.message!!)
        }catch (e: NoInternetException)
        {
            productLisetner?.onFailure(e.message!!)
        }
    }
}
}