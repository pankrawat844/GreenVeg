package com.app.greenveg.ui.otp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.app.greenveg.R
import com.app.greenveg.ui.forgotpassword.ForgotPassActivity
import com.app.greenveg.ui.login.LoginActivity
import com.app.greenveg.utils.Constants
import com.app.greenveg.utils.toast
import kotlinx.android.synthetic.main.activity_otp.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class OtpActivity : AppCompatActivity(), OtpListener, KodeinAware {
    val factory: OtpViewModelFactory by instance()
    var randomNum: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        val viewmodel = ViewModelProviders.of(this, factory).get(OtpViewmodel::class.java)
        viewmodel.listener = this

        sendSms()
        submit.setOnClickListener {
            if (firstPinView.text.toString() == randomNum.toString()) {
                if (intent.getStringExtra("activity") == "login") {
                    Intent(this, ForgotPassActivity::class.java).also {
                        it.putExtra("mobile", intent.getStringExtra("mobile"))
                        startActivity(it)
                    }
                } else {
                    viewmodel.getSignup(
                        intent.getStringExtra("name"),
                        intent.getStringExtra("email"),
                        intent.getStringExtra("password"),
                        intent.getStringExtra("username"),
                        intent.getStringExtra("serviceArea"),
                        intent.getStringExtra("mobile"),
                        intent.getStringExtra("alternatemobile"),
                        intent.getStringExtra("address1"),
                        intent.getStringExtra("address2"),
                        intent.getStringExtra("address3"),
                        intent.getStringExtra("address4"),
                        intent.getStringExtra("address5"),
                        intent.getStringExtra("landmark"),
                        intent.getStringExtra("pincode"),
                        intent.getStringExtra("district"),
                        intent.getStringExtra("state")
                    )
                }
            } else {
                toast("Please enter correct otp.")

            }
        }

        resend.setOnClickListener {
            sendSms()
            toast("Please check your inbox.")
        }

        back.setOnClickListener {
            onBackPressed()
        }
    }


    fun sendSms() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Construct data

                val random = java.util.Random()
                randomNum = random.nextInt(9999 - 1000 + 1) + 1000
                val apiKey = "apikey=" + Constants.txtlocalKey
                val message =
                    "&message=" + "Your otp is " + randomNum + " to register as GREEN VEG customer."
                val sender = "&sender=" + Constants.txtlocalSender
                val numbers = "&numbers=91" + intent.getStringExtra("mobile")

                // Send data
                val conn: HttpURLConnection =
                    URL("https://api.textlocal.in/send/?").openConnection() as HttpURLConnection
                val data = apiKey + numbers + message + sender
                conn.doOutput = true
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Length", Integer.toString(data.length))
                conn.outputStream.write(data.toByteArray(charset("UTF-8")))
                val rd = BufferedReader(InputStreamReader(conn.inputStream))
                val stringBuffer = StringBuffer()
                var line: String? = ""
                while (rd.readLine().also({ line = it }) != null) {
                    stringBuffer.append(line)
                }
                rd.close()
                stringBuffer.toString()
            } catch (e: Exception) {
                println("Error SMS $e")
                "Error $e"
            }
        }
    }

    override fun onStarted() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(user: String) {
        progressBar.visibility = View.GONE
        toast("Signup Successfully. You can now login in Login Page. ")
        finish()
        Intent(this, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }


    }

    override fun onFailure(msg: String) {
        progressBar.visibility = View.GONE
        toast(msg)
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override val kodein by kodein()


}