package com.example.cocktail_dakk.ui.main.mainrecommand

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class BannerViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    private val fragmentlist : ArrayList<Fragment> = ArrayList()
    lateinit var bannerFragment : MainrecimgFragment

    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment {
        return fragmentlist[position]
    }

    fun addFragment(imgRes: String){
        bannerFragment = MainrecimgFragment(imgRes)
        fragmentlist.add(bannerFragment)
        notifyItemInserted(fragmentlist.size-1)
    }
}