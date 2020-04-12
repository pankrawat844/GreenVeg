package com.app.greenveg.ui.contactus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.greenveg.R
import com.app.greenveg.model.Contact
import com.app.greenveg.utils.MyApi
import com.app.greenveg.utils.toast
import kotlinx.android.synthetic.main.fragment_contact_us.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContactUsFragment : Fragment(), KodeinAware {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact_us, container, false)
        view.progressBar.visibility = View.VISIBLE
        MyApi().contact().enqueue(object : Callback<Contact> {
            override fun onFailure(call: Call<Contact>, t: Throwable) {
                view.progressBar.visibility = View.GONE
                activity?.toast(t.message!!)
            }

            override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                view.progressBar.visibility = View.GONE
                view.email.text = response.body()?.data?.response?.email!!
                view.phone.text = response.body()?.data?.response?.mobile!!
            }

        })
        view.phone.setOnClickListener {
            Intent(Intent.ACTION_DIAL).also {
                it.data = Uri.parse("tel:" + view.phone.text.toString())
                activity?.startActivity(it)
            }
        }

        return view
    }

    override val kodein by kodein()


}