package com.app.greenveg

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            Intent(this@SplashActivity, NavigationActivity::class.java).also {
                startActivity(it)
            }
            finish()
        },4000)

    }
}