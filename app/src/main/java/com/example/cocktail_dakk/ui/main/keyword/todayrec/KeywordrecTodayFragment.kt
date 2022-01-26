package com.example.cocktail_dakk.ui.main.keyword.todayrec

import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.CocktailKeywords
import com.example.cocktail_dakk.databinding.FragmentKeywordrecTodayBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.MainFragment

class KeywordrecTodayFragment(val cocktailInfoId : Int,val englishName : String,val koreanName : String,val cocktailKeywords : ArrayList<CocktailKeywords>,
                              val recommendImageURL : String) : BaseFragment<FragmentKeywordrecTodayBinding>(FragmentKeywordrecTodayBinding::inflate) {
    override fun initAfterBinding() {
        Glide.with(this)
            .load(recommendImageURL)
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.detail_star)
            .into(binding.mainKeywordrecTodaycockIv)

        binding.mainKeywordrecTodaycockNameTv.text = koreanName
//        binding.mainKeywordrecTodaycockTagTv1 = cocktailKeywords.keywordName
    }
}