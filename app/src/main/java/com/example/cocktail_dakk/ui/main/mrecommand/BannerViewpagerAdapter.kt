package com.example.cocktail_dakk.ui.main.mrecommand

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class BannerViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    private val fragmentlist : ArrayList<Fragment> = ArrayList()
    lateinit var bannerFragment : MainrecimgFragment

    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment {
        return fragmentlist[position]
    }


    fun addFragment(imgRes: Int, imgRes2: Uri?){
        bannerFragment = MainrecimgFragment(imgRes,imgRes2)
        fragmentlist.add(bannerFragment)
        notifyItemInserted(fragmentlist.size-1)
    }
}