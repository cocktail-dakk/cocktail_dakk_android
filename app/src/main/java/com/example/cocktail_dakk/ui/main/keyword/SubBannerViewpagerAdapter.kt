package com.example.cocktail_dakk.ui.main.keyword

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cocktail_dakk.databinding.FragmentMainrecSubbannerBinding


class SubBannerViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment){
    private val fragmentlist : ArrayList<Fragment> = ArrayList()
    lateinit var bannerFragment : FragmentMainrecSubbaner

    override fun getItemCount(): Int =fragmentlist.size

    override fun createFragment(position: Int): Fragment {
        return fragmentlist[position]
    }
    fun addFragment(imgRes : Int,setString : String,setColor : Int){
        bannerFragment = FragmentMainrecSubbaner(imgRes,setString,setColor)
        fragmentlist.add(bannerFragment)
        notifyItemInserted(fragmentlist.size-1)
    }

}