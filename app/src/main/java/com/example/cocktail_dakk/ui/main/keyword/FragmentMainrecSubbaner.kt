package com.example.cocktail_dakk.ui.main.keyword

import com.example.cocktail_dakk.databinding.FragmentMainrecSubbannerBinding
import com.example.cocktail_dakk.ui.BaseFragment

class FragmentMainrecSubbaner(var imgRes : Int,var setstr :String, val setcolor : Int) : BaseFragment<FragmentMainrecSubbannerBinding>(FragmentMainrecSubbannerBinding::inflate) {
    override fun initAfterBinding() {
        binding.mainKeywordrecBannerIv.setImageResource(imgRes)
        binding.mainKeywordrecSubbannerTv.text=setstr
        binding.mainKeywordrecSubbannerTv.setTextColor(setcolor)
    }
}