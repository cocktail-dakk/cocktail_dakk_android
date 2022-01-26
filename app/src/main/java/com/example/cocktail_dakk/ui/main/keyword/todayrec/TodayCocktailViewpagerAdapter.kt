package com.example.cocktail_dakk.ui.main.keyword.todayrec

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cocktail_dakk.ui.main.keyword.todayrec.Dailyrecservice.CocktailKeyword

class TodayCocktailViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment)
{
    private val fragmentlist : ArrayList<Fragment> = ArrayList()
    lateinit var keywordfragmentitem : KeywordrecTodayFragment

    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment {
        return fragmentlist[position]
    }

    fun addFragment(cocktailInfoId : Int, englishName : String, koreanName : String, cocktailKeywords : List<CocktailKeyword>, recommendImageURL : String){
        keywordfragmentitem = KeywordrecTodayFragment(cocktailInfoId, englishName, koreanName, cocktailKeywords,recommendImageURL)
        fragmentlist.add(keywordfragmentitem)
        notifyItemChanged(fragmentlist.size-1)
    }

}

//class SubBannerViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment){
//    private val fragmentlist : ArrayList<Fragment> = ArrayList()
//    lateinit var bannerFragment : FragmentMainrecSubbaner
//
//    override fun getItemCount(): Int =fragmentlist.size
//
//    override fun createFragment(position: Int): Fragment {
//        return fragmentlist[position]
//    }
//    fun addFragment(imgRes : Int,setString : String,setColor : Int){
//        bannerFragment = FragmentMainrecSubbaner(imgRes,setString,setColor)
//        fragmentlist.add(bannerFragment)
//        notifyItemInserted(fragmentlist.size-1)
//    }
//
//}