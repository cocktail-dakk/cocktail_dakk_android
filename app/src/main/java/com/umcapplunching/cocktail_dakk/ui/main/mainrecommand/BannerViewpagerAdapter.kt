package com.umcapplunching.cocktail_dakk.ui.main.mainrecommand

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class BannerViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    private val fragmentlist : ArrayList<Fragment> = ArrayList()
    lateinit var bannerFragment : MainrecimgFragment

    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment {
        return fragmentlist[position]
    }

    fun addFragment(cocktailid : Int, imgRes: String){
        MainrecimgFragment(cocktailid = cocktailid, imgRes = imgRes).apply { bannerFragment = this }
        fragmentlist.add(bannerFragment)
        notifyItemInserted(fragmentlist.size-1)
    }
}