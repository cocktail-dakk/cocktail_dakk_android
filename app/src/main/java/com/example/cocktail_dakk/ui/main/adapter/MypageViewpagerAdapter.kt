package com.example.cocktail_dakk.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cocktail_dakk.ui.mypage.MypageResettingDosuFragment
import com.example.cocktail_dakk.ui.mypage.MypageResettingGijuFragment
import com.example.cocktail_dakk.ui.mypage.MypageResettingKeywordFragment


class MypageViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MypageResettingDosuFragment()
            1 -> MypageResettingGijuFragment()
            else -> MypageResettingKeywordFragment()
        }
    }

}