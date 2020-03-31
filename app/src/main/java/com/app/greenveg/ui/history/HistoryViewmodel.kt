package com.app.greenveg.ui.history

import androidx.lifecycle.ViewModel
import com.app.greenveg.model.Signup
import com.app.greenveg.repo.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewmodel(val repository: Repository) : ViewModel() {
    var historyListener: HistoryListener? = null
    fun getHistory(userId: String) {
        historyListener?.onStarted()
        repository.getHistory(userId).enqueue(object : Callback<HistoryData> {

            override fun onFailure(call: Call<HistoryData>, t: Throwable) {
                historyListener?.onFailour(t.message!!)
            }

            override fun onResponse(call: Call<HistoryData>, response: Response<HistoryData>) {
                if (response.isSuccessful) {
                    if (response.body()?.data?.success!!) {
                        historyListener?.onSuccess(response.body()!!)
                    } else {
                        historyListener?.onFailour("Something went wrong, please try again.")

                    }
                } else {
                    historyListener?.onFailour("Something went wrong, please try again.")

                }
            }

        })
    }

    fun getHistoryDetail(orderid: String) {
        repository.getHistoryDetail(orderid).enqueue(object : Callback<HistoryDetailItem> {
            override fun onFailure(call: Call<HistoryDetailItem>, t: Throwable) {
                historyListener?.onFailour(t.message!!)
            }

            override fun onResponse(
                call: Call<HistoryDetailItem>,
                response: Response<HistoryDetailItem>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.data?.success!!) {
                        historyListener?.onSuccessDetail(response.body()!!)
                    }
                }
            }

        })
    }

    fun cancelOrder(orderid: String) {
        historyListener?.onStarted()
        repository.cancelOrder(orderid).enqueue(object : Callback<Signup> {
            override fun onFailure(call: Call<Signup>, t: Throwable) {
                historyListener?.onFailour(t.message!!)
            }

            override fun onResponse(
                call: Call<Signup>,
                response: Response<Signup>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.data?.success!!) {
                        historyListener?.onSuccessCancel(response.body()!!)
                    }
                }
            }

        })
    }
}