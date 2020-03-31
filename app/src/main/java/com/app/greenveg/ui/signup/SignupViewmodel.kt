package com.app.greenveg.ui.signup

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.app.greenveg.model.ServiceArea
import com.app.greenveg.repo.Repository
import com.app.greenveg.ui.otp.OtpActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewmodel(val repository: Repository) : ViewModel() {
    var signupListene: SignupListener? = null
    var name: String? = ""
    var email: String? = ""
    var password: String? = ""
    var confirmedpassword: String? = ""
    var username: String? = ""
    var servicearea: Int? = null
    var serviceareaList: List<String>? = null

    var mobile: String? = ""
    var alternate_phone: String? = ""
    var address_line1: String? = ""
    var address_line2: String? = ""
    var address_line3: String? = ""
    var address_line4: String? = ""
    var address_line5: String? = "Bhubaneshwar"
    var landmark: String? = ""
    var pincode: String? = ""
    var district: String? = "Khurda"
    var state: String? = "Ordisha"
    fun getServiceArea() {
        repository.getServiceArea().enqueue(object : Callback<ServiceArea> {
            override fun onFailure(call: Call<ServiceArea>, t: Throwable) {
                signupListene?.onFailure(t.message!!)
            }

            override fun onResponse(call: Call<ServiceArea>, response: Response<ServiceArea>) {
                if (response.isSuccessful) {
                    signupListene?.onServiceAreaSucces(response.body()!!)
                } else {
                    signupListene?.onFailure(
                        JSONObject(response.errorBody()?.string()!!).getString(
                            "message"
                        )
                    )
                }
            }

        })
    }

    fun getSignup1(view: View) {
        if (serviceareaList?.get(servicearea!!) == "Select Service Area") {
//            Toast.makeText(
//                view.context,
//                "Please Select Service Area.",
//                Toast.LENGTH_LONG
//            ).show()
            signupListene?.onFailure("Please Select Service Area.")
            return
        }
        if (name.isNullOrEmpty()) {
//            Toast.makeText(view.context, "Please enter Name.", Toast.LENGTH_LONG).show()
            signupListene?.onFailure("Please enter name.")

            return
        }
        if (username.isNullOrEmpty()) {
//            Toast.makeText(view.context, "Please enter Username.", Toast.LENGTH_LONG).show()
            signupListene?.onFailure("Please enter Username.")
            return
        }
        if (password.isNullOrEmpty()) {
//            Toast.makeText(view.context, "Please enter Password.", Toast.LENGTH_LONG).show()
            signupListene?.onFailure("Please enter Password.")

            return
        }

        if (mobile.isNullOrEmpty()) {
//            Toast.makeText(view.context, "Please enter Mobile No.", Toast.LENGTH_LONG).show()
            signupListene?.onFailure("Please enter Mobile No.")
            return
        }
        if (mobile?.length!! < 10) {
//            Toast.makeText(view.context, "Please enter 10 digit Mobile No.", Toast.LENGTH_LONG).show()
            signupListene?.onFailure("Please enter 10 digit Mobile No.")

            return
        }

        if (password?.length!! < 6 || password?.length!! > 10) {
//            Toast.makeText(view.context, "Please enter 10 digit Mobile No.", Toast.LENGTH_LONG).show()
            signupListene?.onFailure("Password would be minimum 6 and maximum 10 character having at least one letter and one number.")
            return
        }
        if (!password!!.trim { it <= ' ' }
                .matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$".toRegex())) {
            signupListene?.onFailure("Password would be minimum 6 and maximum 10 character having at least one letter and one number.")
            return
        }
        if (password != confirmedpassword) {
//            Toast.makeText(
//                view.context,
//                "Password and Confirm Password is not same.",
//                Toast.LENGTH_LONG
//            ).show()
            signupListene?.onFailure("Password and Confirm Password is not same.")

            return
        }

        signupListene?.onsecondViewVisble()
    }

    fun back(view: View) {
        signupListene?.onback()
    }

    fun getSignup2(view: View) {
        if (address_line1.isNullOrEmpty()) {
//            Toast.makeText(view.context, "Please enter Address Line 1.", Toast.LENGTH_LONG).show()
            signupListene?.onFailure("Please enter Address Line 1.")
            return
        }
        if (address_line2.isNullOrEmpty()) {
//            Toast.makeText(view.context, "Please enter Address Line 2.", Toast.LENGTH_LONG).show()
            signupListene?.onFailure("Please enter Address Line 2.")
            return
        }

        if (pincode.isNullOrEmpty()) {
//            Toast.makeText(view.context, "Please enter Pincode.", Toast.LENGTH_LONG).show()
            signupListene?.onFailure("Please enter Pincode.")
            return
        }
        if (pincode?.length != 6) {
//            Toast.makeText(view.context, "Please enter 6 digit Pincode.", Toast.LENGTH_LONG).show()
            signupListene?.onFailure("Please enter 6 digit Pincode.")
            return
        }

        if (district.isNullOrEmpty()) {
//            Toast.makeText(view.context, "Please enter District.", Toast.LENGTH_LONG).show()
            signupListene?.onFailure("Please enter District.")

            return
        }
        if (state.isNullOrEmpty()) {
//            Toast.makeText(view.context, "Please enter State.", Toast.LENGTH_LONG).show()
            signupListene?.onFailure("Please enter State.")

            return
        }

        Intent(view.context, OtpActivity::class.java).also {
            it.putExtra("name", name)
            it.putExtra("email", email)
            it.putExtra("password", password)
            it.putExtra("username", username)
            it.putExtra("serviceArea", serviceareaList?.get(servicearea!!))
            it.putExtra("mobile", mobile)
            it.putExtra("alternatemobile", alternate_phone)
            it.putExtra("address1", address_line1)
            it.putExtra("address2", address_line2)
            it.putExtra("address3", address_line3)
            it.putExtra("address4", address_line4)
            it.putExtra("address5", address_line5)
            it.putExtra("landmark", landmark)
            it.putExtra("pincode", pincode)
            it.putExtra("district", district)
            it.putExtra("state", state)
            view.context.startActivity(it)
        }
    }
}