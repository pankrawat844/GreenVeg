
package com.app.greenveg.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.app.greenveg.R
import com.app.greenveg.fragment.ProductListFragment
import com.app.greenveg.model.Category
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(),HomeListener,KodeinAware {
    lateinit var viewPager: ViewPager
    lateinit var tabLayout:TableLayout
    val factory:HomeViewModelFactory by instance()
    var pagerAdapter:MyViewPagerAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var viewmodel: MainActivityViewModel =ViewModelProviders.of(this,factory).get(
            MainActivityViewModel::class.java)
        viewmodel.homeListener=this
        viewmodel.getAllCategory()


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

    override fun onSuccess(response: List<Category.Response>) {
        Log.e("TAG", "onSuccess: "+response.toString() )
        pagerAdapter= MyViewPagerAdapter(supportFragmentManager)
        for (i in response.iterator()) {
            pagerAdapter?.addFragment(ProductListFragment(),i.categoryName!!)
        }
        viewpager1.adapter=pagerAdapter
        tablayout1.setupWithViewPager(viewpager1,true)
    }

    override fun onFailour(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override val kodein by kodein()
}