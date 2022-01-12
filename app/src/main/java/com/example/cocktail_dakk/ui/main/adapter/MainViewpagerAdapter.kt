package com.example.cocktail_dakk.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cocktail_dakk.ui.main.keyword.KeywordrecommandFragment
import com.example.cocktail_dakk.ui.main.mrecommand.MainrecommandFragment

//import com.cock_tail.test_xml.ui.main.keyword.KeywordrecommandFragment
//import com.cock_tail.test_xml.ui.main.mrecommand.MainrecommandFragment


class MainViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){//when은 swich문과 동일
            0 -> MainrecommandFragment()
            else -> KeywordrecommandFragment()
        }
    }

}