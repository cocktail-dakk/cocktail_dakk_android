package com.example.cocktail_dakk.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cocktail_dakk.ui.mypage.MypageResettingDosuFragment
import com.example.cocktail_dakk.ui.mypage.MypageResettingGijuFragment
import com.example.cocktail_dakk.ui.mypage.MypageResettingKeywordFragment
import com.example.cocktail_dakk.ui.start.setting.fragment.StartDDrinkcapFragment
import com.example.cocktail_dakk.ui.start.setting.fragment.StartELikeFragment
import com.example.cocktail_dakk.ui.start.setting.fragment.StartFKeywordFragment


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