package com.app.greenveg.ui.profile


import android.view.View
import androidx.lifecycle.ViewModel
import com.app.greenveg.model.ServiceArea
import com.app.greenveg.model.Signup
import com.app.greenveg.repo.Repository
import com.app.greenveg.ui.signup.SignupListener
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(val repository: Repository) : ViewModel() {
    var signupListene: SignupListener? = null
    var userId: String? = ""
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
        if (!password!!.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$".toRegex())) {
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
        signupListene?.onStarted()
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

        repository.updateProfile(
            userId!!,
            name!!,
            email,
            password,
            username,
            serviceareaList?.get(servicearea!!),
            mobile,
            alternate_phone,
            address_line1,
            address_line2,
            address_line3,
            address_line4,
            address_line5,
            landmark,
            pincode,
            district,
            state
        ).enqueue(object : Callback<Signup> {
            override fun onFailure(call: retrofit2.Call<Signup>, t: Throwable) {
                signupListene?.onFailure(t.message!!)
            }

            override fun onResponse(call: retrofit2.Call<Signup>, response: Response<Signup>) {
                if (response.isSuccessful) {
//                    val jsonObject = JSONObject(response.body().toString())
                    val data = response.body()
                    if (data?.data?.success!!) {
                        signupListene?.onSuccess(data.data.msg!!)
                    } else {
                        signupListene?.onFailure(data.data.msg!!)
                    }
                } else {
                    signupListene?.onFailure(
                        JSONObject(response.errorBody()?.string()!!).getString(
                            "msg"
                        )
                    )
                }
            }

        })
    }
}
