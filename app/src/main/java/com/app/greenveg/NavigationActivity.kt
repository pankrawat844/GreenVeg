package com.app.greenveg

import android.content.*
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.greenveg.db.AppDatabase
import com.app.greenveg.ui.cart.CartActivity
import com.app.greenveg.ui.login.LoginActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.toolbar_home.cart_item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NavigationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.history, R.id.profile, R.id.logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        CoroutineScope(Dispatchers.IO).launch {
            val size = AppDatabase(this@NavigationActivity).cartDao().getCartProduct().size
            withContext(Dispatchers.Main) {
                cart_item.text = size.toString()
            }
        }

        cart.setOnClickListener {
            Intent(this, CartActivity::class.java).also {
                startActivity(it)
            }
        }

        if (!getSharedPreferences("greenveg", Context.MODE_PRIVATE).getBoolean("islogin", false)) {
            navView.menu.getItem(1).isVisible = false
            navView.menu.getItem(2).isVisible = false
            navView.menu.getItem(6).isVisible = false
            navView.menu.getItem(5).isVisible = true
            navView.menu.getItem(0).isChecked = true
        } else {
            navView.menu.getItem(5).isVisible = false
        }
        if (intent.getBooleanExtra("from_cart", false)) {
            navController.navigate(R.id.action_firstFragment_to_secondFragment)

        }
        navView.menu.findItem(R.id.login)
            .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    Intent(this@NavigationActivity, LoginActivity::class.java).also {
                        startActivity(it)
                    }
                    return true
                }

            })
        navView.menu.findItem(R.id.payment).setOnMenuItemClickListener(
            object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    val alertDialog = AlertDialog.Builder(this@NavigationActivity)
                    alertDialog.setTitle("Payment")
                    alertDialog.setMessage("We receive Cash on Delivery. We accept all Debit/Credit Cards, Paytm, Google Pay, PhonePe, BHIM UPI.")
                    alertDialog.setPositiveButton(
                        "OK",
                        DialogInterface.OnClickListener { dialog, which ->

                            dialog.dismiss()

                        })

                    alertDialog.create().show()
                    drawerLayout.closeDrawers()
                    return false
                }
            })

        navView.menu.findItem(R.id.logout)
            .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
//                toast("logout")
                    val alertDialog = AlertDialog.Builder(this@NavigationActivity)
                    alertDialog.setTitle("Logout")
                    alertDialog.setMessage("Are you sure to logout.")
                    alertDialog.setPositiveButton(
                        "Yes",
                        DialogInterface.OnClickListener { dialog, which ->
                            Intent(this@NavigationActivity, NavigationActivity::class.java).also {
                                getSharedPreferences("greenveg", Context.MODE_PRIVATE).edit()
                                    .clear().apply()
                                it.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(it)


                            }
                        })
                    alertDialog.setNegativeButton(
                        "No",
                        DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })
                    alertDialog.create().show()
                    return true
                }

            })

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.history || destination.id == R.id.profile) {
                home.visibility = View.VISIBLE
            } else
                home.visibility = View.GONE
        }

        home.setOnClickListener {
            Intent(this@NavigationActivity, NavigationActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(it)


            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.currentDestination
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    companion object {
        private lateinit var context: Context
        fun changeCartValue(con: Context) {
            val size = AppDatabase(con).cartDao().getCartProduct().size

        }
    }

    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {

            when (intent?.action) {
                "change_value" -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        val size =
                            AppDatabase(this@NavigationActivity).cartDao().getCartProduct().size
                        withContext(Dispatchers.Main) {
                            cart_item.text = size.toString()
                        }
                    }

                }


            }
        }

    }


    override fun onStart() {
        super.onStart()

        registerReceiver(broadCastReceiver, IntentFilter("change_value"))
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            val size = AppDatabase(this@NavigationActivity).cartDao().getCartProduct().size
            withContext(Dispatchers.Main) {
                cart_item.text = size.toString()
            }
        }


    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadCastReceiver)
    }
}