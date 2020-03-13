
package com.app.greenveg.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.app.greenveg.R
import com.app.greenveg.db.AppDatabase
import com.app.greenveg.fragment.productlist.ProductListFragment
import com.app.greenveg.model.Category
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(),HomeListener,KodeinAware {
    lateinit var viewPager: ViewPager
    lateinit var tabLayout:TableLayout
    val factory:HomeViewModelFactory by instance()
    var pagerAdapter:MyViewPagerAdapter?=null
    var cartitem:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cartitem = cart_item
        var viewmodel: MainActivityViewModel = ViewModelProviders.of(this, factory).get(
            MainActivityViewModel::class.java
        )
        viewmodel.homeListener = this
        viewmodel.getAllCategory()
        CoroutineScope(Dispatchers.IO).launch {
            val size = AppDatabase(this@MainActivity).cartDao().getCartProduct().size
            withContext(Dispatchers.Main) {
                cart_item.text = size.toString()
            }
        }
    }

   class MyViewPagerAdapter(fm:FragmentManager):FragmentStatePagerAdapter(fm)
   {
       val fragmentList:MutableList<Fragment>?=ArrayList<Fragment>()
       val fragmentTitleList:MutableList<String> = ArrayList<String>()
       override fun getItem(position: Int): Fragment {
            return fragmentList?.get(position)!!
       }

       override fun getCount(): Int {
          return fragmentList?.size!!
       }
       override fun getPageTitle(position: Int): CharSequence? {
           return fragmentTitleList.get(position)
       }

       fun addFragment(fragment: Fragment,title:String){
           fragmentList?.add(fragment)
           fragmentTitleList.add(title)
       }
   }

    override fun onStarted() {
        mainProgress.visibility=View.VISIBLE
    }

    override fun onSuccess(response: List<Category.Response>) {
        mainProgress.visibility=View.GONE
        Log.e("TAG", "onSuccess: "+response.toString() )
        pagerAdapter= MyViewPagerAdapter(supportFragmentManager)
        for (i in response.iterator()) {
            val fragment=
                ProductListFragment()
            val bundle=Bundle()
            bundle.putString("cat_id",i.cid)
            bundle.putString("cat_name",i.categoryName)
            fragment.arguments=bundle
            pagerAdapter?.addFragment(fragment,i.categoryName!!)
        }
        viewpager1.adapter=pagerAdapter
        tablayout1.setupWithViewPager(viewpager1,true)
    }

    override fun onFailour(msg: String) {
        mainProgress.visibility=View.GONE
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override val kodein by kodein()

    companion object{
        private lateinit var context: Context
        fun  changeCartValue(con: Context){
           val size= AppDatabase(con).cartDao().getCartProduct().size

        }
    }
    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {

            when (intent?.action) {
                "change_value" -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        val size = AppDatabase(this@MainActivity).cartDao().getCartProduct().size
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

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadCastReceiver)
    }
}