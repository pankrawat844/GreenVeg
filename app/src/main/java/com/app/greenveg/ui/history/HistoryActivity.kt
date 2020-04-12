package com.app.greenveg.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.greenveg.R
import com.app.greenveg.model.Signup
import com.app.greenveg.utils.RecyclerItemClickListener
import com.app.greenveg.utils.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_history.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HistoryActivity : Fragment(), KodeinAware, HistoryListener {
    val factory: HistoryViewModelFactory by instance()
    val newlist = ArrayList<HistoryItem>()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_history)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            LayoutInflater.from(activity).inflate(R.layout.activity_history, container, false)

        return view!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewmodel = ViewModelProviders.of(this, factory).get(HistoryViewmodel::class.java)
        viewmodel.historyListener = this
        viewmodel.getHistory(
            activity?.getSharedPreferences("greenveg", Context.MODE_PRIVATE)
                ?.getString("userid", "")!!
        )
        historyRecyclerview.addOnItemTouchListener(RecyclerItemClickListener(activity,
            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    Intent(activity, HistoryDetailActivity::class.java).also {
                        it.putExtra("orderid", newlist.get(position).orderId)
                        startActivity(it)
                    }
                }

            }))
    }

    override val kodein: Kodein by kodein()
    override fun onStarted() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(response: HistoryData) {
        progressBar.visibility = View.GONE
        newlist.clear()
        val adapter = GroupAdapter<GroupieViewHolder>().apply {
            val list = response.data.response

            for (item in list) {
                for (newitem in newlist) {
                    if (newitem.orderId == item.orderId) {
                        return
                    }

                }

                newlist.add(item)
            }
            addAll(newlist.changeAdapter())
        }
        historyRecyclerview.layoutManager = LinearLayoutManager(activity)
        historyRecyclerview.adapter = adapter
    }

    override fun onFailour(msg: String) {
        progressBar.visibility = View.GONE
        activity?.toast(msg)
    }

    override fun onSuccessDetail(response: HistoryDetailItem) {

    }

    override fun onSuccessDelivery(response: HistoryDetailItem) {

    }

    override fun onSuccessCancel(response: Signup) {

    }
}


private fun List<HistoryItem>.changeAdapter(): List<HistoryAdapter> {
    return this.map {
        HistoryAdapter(it)
    }

}
