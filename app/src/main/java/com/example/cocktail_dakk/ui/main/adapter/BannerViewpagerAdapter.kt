//package com.cock_tail.test_xml.ui.main.adapter
package com.example.cocktail_dakk.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cocktail_dakk.ui.main.mrecommand.MainrecimgFragment

//import com.cock_tail.test_xml.ui.main.mrecommand.MainrecimgFragment

//cocktail_dakk

class BannerViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    private val fragmentlist : ArrayList<Fragment> = ArrayList()
    lateinit var bannerFragment : MainrecimgFragment

    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment {
        return fragmentlist[position]
    }


//    fun addFragment(fragment: Fragment){
//        fragmentlist.add(fragment)
//        notifyItemInserted(fragmentlist.size-1) //viewpager2 에게 새로운인자가 있음을 알려줌
//    }

    fun addFragment(imgRes : Int){
        bannerFragment = MainrecimgFragment(imgRes)
        fragmentlist.add(bannerFragment)
        notifyItemInserted(fragmentlist.size-1)
    }
}