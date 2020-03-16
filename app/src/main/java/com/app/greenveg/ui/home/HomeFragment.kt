package com.app.greenveg.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProviders
import com.app.greenveg.R
import com.app.greenveg.model.Category
import com.app.greenveg.ui.productlist.ProductListFragment
import com.app.greenveg.ui.search.SearchActivity
import com.app.greenveg.utils.toast
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class HomeFragment : Fragment(), HomeListener, KodeinAware {
    val factory: HomeViewModelFactory by instance()
    var pagerAdapter: MyViewPagerAdapter? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        var viewmodel: MainActivityViewModel = ViewModelProviders.of(this, factory).get(
            MainActivityViewModel::class.java
        )
        viewmodel.homeListener = this
        viewmodel.getAllCategory()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.searc.setOnClickListener {
            if (view.search_txt.text.toString() == "") {
                context?.toast("Please Enter Search Value")
            } else {
                Intent(activity, SearchActivity::class.java).also {
                    it.putExtra("search", view.search_txt.text.toString())
                    startActivity(it)
                }
            }
        }

    }

    class MyViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        val fragmentList: MutableList<Fragment>? = ArrayList<Fragment>()
        val fragmentTitleList: MutableList<String> = ArrayList<String>()
        override fun getItem(position: Int): Fragment {
            return fragmentList?.get(position)!!
        }

        override fun getCount(): Int {
            return fragmentList?.size!!
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList.get(position)
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList?.add(fragment)
            fragmentTitleList.add(title)
        }
    }


    override fun onStarted() {
        mainProgress.visibility = View.VISIBLE
    }

    override fun onSuccess(response: List<Category.Response>) {
        mainProgress.visibility = View.GONE
        Log.e("TAG", "onSuccess: " + response.toString())
        pagerAdapter = MyViewPagerAdapter(childFragmentManager)
        for (i in response.iterator()) {
            val fragment =
                    ProductListFragment()
            val bundle = Bundle()
            bundle.putString("cat_id", i.cid)
            bundle.putString("cat_name", i.categoryName)
            fragment.arguments = bundle
            pagerAdapter?.addFragment(fragment, i.categoryName!!)
        }
        viewpager1.adapter = pagerAdapter
        tablayout1.setupWithViewPager(viewpager1, true)
    }

    override fun onFailour(msg: String) {
        mainProgress.visibility = View.GONE
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override val kodein: Kodein by kodein()


}