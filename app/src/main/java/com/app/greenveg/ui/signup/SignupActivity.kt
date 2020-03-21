package com.app.greenveg.ui.signup

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.app.greenveg.R
import com.app.greenveg.model.ServiceArea
import com.app.greenveg.model.User
import kotlinx.android.synthetic.main.activity_signup.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(), KodeinAware, SignupListener {
    val factory: SignupViewmodelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val viewmodel = ViewModelProviders.of(this, factory).get(SignupViewmodel::class.java)
        viewmodel.signupListene = this
        viewmodel.getServiceArea()
    }

    override val kodein by kodein()

    override fun onStarted() {

    }

    override fun onSuccess(user: User) {

    }

    override fun onFailure(msg: String) {

    }

    override fun onServiceAreaSucces(area: ServiceArea) {
        service_area.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            area.response.getName()
        )
    }
}

private fun List<ServiceArea.Response>.getName(): List<String> {
    return this.map {
        it.areaName
    }
}
