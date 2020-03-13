package com.app.greenveg.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.app.greenveg.utils.Constants
import com.squareup.picasso.Picasso


class ProductImageViewPage(val ctx: Context, val list: List<String>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = ctx.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater
        val view: View = layoutInflater.inflate(com.app.greenveg.R.layout.custom_layout, null)
        val imageView: ImageView =
            view.findViewById<View>(com.app.greenveg.R.id.imageView) as ImageView
        Picasso.get().load(Constants.imageUrl + list[position]).into(imageView)

        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}