package com.example.cocktail_dakk.ui.main.keyword.todayrec

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentKeywordrecTodayBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.keyword.todayrec.Dailyrecservice.CocktailKeyword

class KeywordrecTodayFragment(val cocktailInfoId : Int, val englishName : String, val koreanName : String, val cocktailKeyword : List<CocktailKeyword>,
                              val recommendImageURL : String) : BaseFragment<FragmentKeywordrecTodayBinding>(FragmentKeywordrecTodayBinding::inflate) {
    override fun initAfterBinding() {
        Glide.with(this)
            .load("https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/today/BlueStar.webp")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.detail_star)
            .into(binding.mainKeywordrecTodaycockIv)

        binding.mainKeywordrecTodaycockNameTv.text = koreanName
        binding.mainKeywordrecTodaycockTagTv1.text = cocktailKeyword[0].keywordName
    }
}