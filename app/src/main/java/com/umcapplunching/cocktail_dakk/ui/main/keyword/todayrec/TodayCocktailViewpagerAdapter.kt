package com.umcapplunching.cocktail_dakk.ui.main.keyword.todayrec

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umcapplunching.cocktail_dakk.ui.search.searchService.Keyword



class TodayCocktailViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment)
{
    private val fragmentlist : ArrayList<Fragment> = ArrayList()
    lateinit var keywordfragmentitem : KeywordrecTodayFragment

    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment {
        return fragmentlist[position]
    }

    fun addFragment(position: Int,cocktailInfoId : Int, englishName : String, koreanName : String, cocktailKeywords : List<Keyword>, recommendImageURL : String){
        keywordfragmentitem = KeywordrecTodayFragment(position,cocktailInfoId, englishName, koreanName, cocktailKeywords,recommendImageURL)
        fragmentlist.add(keywordfragmentitem)
        notifyItemChanged(fragmentlist.size-1)
    }

}

