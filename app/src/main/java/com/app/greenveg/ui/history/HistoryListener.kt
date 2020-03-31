package com.app.greenveg.ui.history

import com.app.greenveg.model.Signup

interface HistoryListener {
    fun onStarted()
    fun onSuccess(response: HistoryData)
    fun onSuccessDetail(response: HistoryDetailItem)
    fun onSuccessCancel(response: Signup)
    fun onFailour(msg: String)

}
