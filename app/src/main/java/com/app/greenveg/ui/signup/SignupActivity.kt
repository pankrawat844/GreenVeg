package com.app.greenveg.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.app.greenveg.R
import com.app.greenveg.databinding.ActivitySignupBinding
import com.app.greenveg.model.ServiceArea
import com.app.greenveg.utils.Item
import com.app.greenveg.utils.toast
import kotlinx.android.synthetic.main.activity_signup.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class SignupActivity : AppCompatActivity(), KodeinAware, SignupListener {
    val factory: SignupViewmodelFactory by instance()
    var databind: ActivitySignupBinding? = null
    var serviceAreaList: ArrayList<String>? = null
    var viewmodel: SignupViewmodel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databind = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewmodel = ViewModelProviders.of(this, factory).get(SignupViewmodel::class.java)
        viewmodel?.signupListene = this
        databind?.data = viewmodel
        databind?.item = Item()
        val filter = InputFilter { source, start, end, dest, dstart, dend ->

            for (i in start until end) {

                if (Character.isWhitespace(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }
        username.filters = arrayOf(filter)
        password.filters = arrayOf(filter, LengthFilter(10))
        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })

//        viewmodel.servicearea=serviceAreaList?.get(databind?.item?.selectedItemPosition!!)
        service_area.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                databind?.item?.selectedItemPosition=position
//                viewmodel?.servicearea= serviceAreaList?.get(position)
            }

        }
        viewmodel?.getServiceArea()
    }

    override val kodein by kodein()

    override fun onStarted() {

    }

    override fun onSuccess(user: String) {

    }

    override fun onFailure(msg: String) {
        toast(msg)
    }

    override fun onServiceAreaSucces(area: ServiceArea) {
        serviceAreaList = area.response.getName()
        service_area.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            serviceAreaList!!
        )
        serviceAreaList?.add(0, "Select Service Area")
        viewmodel?.serviceareaList = serviceAreaList
    }

    override fun onsecondViewVisble() {
        view1.visibility = GONE
        view2.visibility = VISIBLE
    }

    override fun onback() {
        view1.visibility = VISIBLE
        view2.visibility = GONE
    }
}

private fun ArrayList<ServiceArea.Response>.getName(): ArrayList<String> {
    return this.map {
        it.areaName
    } as ArrayList<String>
}
