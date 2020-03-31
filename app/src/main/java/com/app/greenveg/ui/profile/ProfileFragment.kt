package com.app.greenveg.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.greenveg.R
import com.app.greenveg.databinding.ProfileFragmentBinding
import com.app.greenveg.model.ServiceArea
import com.app.greenveg.ui.signup.SignupListener
import com.app.greenveg.utils.Item
import com.app.greenveg.utils.toast
import kotlinx.android.synthetic.main.profile_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware, SignupListener {
    var sharedPreferences: SharedPreferences? = null
    val factory: ProfileViewModelFactory by instance()
    var serviceAreaList: ArrayList<String>? = null
    var view: ProfileFragmentBinding? = null

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = DataBindingUtil.inflate<ProfileFragmentBinding>(
            inflater,
            R.layout.profile_fragment,
            container,
            false
        )
        viewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel::class.java)
        view?.data = viewModel

        viewModel.signupListene = this
        viewModel.getServiceArea()
        view?.item = Item()
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (Character.isWhitespace(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }
        view?.username?.filters = arrayOf(filter)
        view?.password?.filters = arrayOf(filter)
        sharedPreferences = activity?.getSharedPreferences("greenveg", Context.MODE_PRIVATE)
        viewModel.userId = sharedPreferences?.getString("userid", "")
        viewModel.name = sharedPreferences?.getString("name", "")
        viewModel.username = sharedPreferences?.getString("username", "")
        viewModel.email = (sharedPreferences?.getString("email", ""))
        viewModel.mobile = (sharedPreferences?.getString("mobile", ""))
        viewModel.password = (sharedPreferences?.getString("password", ""))
        viewModel.confirmedpassword = (sharedPreferences?.getString("password", ""))
        viewModel.alternate_phone = (sharedPreferences?.getString("alternatemobile", ""))
        viewModel.address_line1 = (sharedPreferences?.getString("address1", ""))
        viewModel.address_line2 = (sharedPreferences?.getString("address2", ""))
        viewModel.address_line3 = (sharedPreferences?.getString("address3", ""))
        viewModel.address_line4 = (sharedPreferences?.getString("address4", ""))
        viewModel.address_line5 = (sharedPreferences?.getString("address5", ""))
        viewModel.landmark = (sharedPreferences?.getString("landmark", ""))
        viewModel.pincode = (sharedPreferences?.getString("pincode", ""))
        viewModel.district = (sharedPreferences?.getString("district", ""))
        viewModel.state = (sharedPreferences?.getString("state", ""))

        return view?.root
    }


    override fun onStarted() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(user: String) {
        progressBar.visibility = View.GONE
        activity?.toast(user)
        val sharedPreferences = activity?.getSharedPreferences("greenveg", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit {


            putString("name", viewModel.name)
            putString("username", viewModel.username)
            putString("email", viewModel.email)
            putString("mobile", viewModel.mobile)
            putString("password", viewModel.password)
            putString("servicearea", viewModel.serviceareaList?.get(viewModel.servicearea!!))
            putString("alternatemobile", viewModel.alternate_phone)
            putString("address1", viewModel.address_line1)
            putString("address2", viewModel.address_line2)
            putString("address3", viewModel.address_line3)
            putString("address4", viewModel.address_line4)
            putString("address5", viewModel.address_line5)
            putString("landmark", viewModel.landmark)
            putString("pincode", viewModel.pincode)
            putString("district", viewModel.district)
            putString("state", viewModel.state)
            commit()
        }
    }

    override fun onFailure(msg: String) {
        progressBar.visibility = View.GONE
        activity?.toast(msg)
    }

    override fun onServiceAreaSucces(area: ServiceArea) {
        serviceAreaList = area.response.getName()
        view?.serviceArea?.adapter = ArrayAdapter(
            activity!!,
            android.R.layout.simple_spinner_dropdown_item,
            serviceAreaList!!
        )
        serviceAreaList?.add(0, "Select Service Area")
        viewModel.serviceareaList = serviceAreaList
        for (area in serviceAreaList!!) {
            if (area.equals(sharedPreferences?.getString("servicearea", ""))) {
                service_area.setSelection(serviceAreaList!!.indexOf(area))
                return
            }
        }
    }

    override fun onsecondViewVisble() {
        view?.view1?.visibility = View.GONE
        view?.view2?.visibility = View.VISIBLE
    }

    override fun onback() {
        view1.visibility = View.VISIBLE
        view2.visibility = View.GONE
    }

    override val kodein by kodein()

    private fun ArrayList<ServiceArea.Response>.getName(): ArrayList<String> {
        return this.map {
            it.areaName
        } as ArrayList<String>
    }
}