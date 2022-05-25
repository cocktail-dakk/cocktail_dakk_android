package com.umcapplunching.cocktail_dakk.ui.main.keyword.todayrec

import android.content.Intent
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.FragmentKeywordrecTodayBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.search.searchService.Keyword
import com.tbuonomo.viewpagerdotsindicator.setPaddingVertical
import com.umcapplunching.cocktail_dakk.ui.menu_detail.MenuDetailActivity
import kotlin.math.min

//import com.umcapplunching.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService.CocktailKeyword

class KeywordrecTodayFragment(val position : Int,val cocktailInfoId : Int,
                              val englishName : String, val koreanName : String,
                              val cocktailKeyword : List<Keyword>,
                              val recommendImageURL : String) : BaseFragment<FragmentKeywordrecTodayBinding>(FragmentKeywordrecTodayBinding::inflate) {
    override fun initAfterBinding() {
        Glide.with(this)
            .load(recommendImageURL)
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.img_cocktail_alaskaicedtea_dailyrec)
            .into(binding.mainKeywordrecTodaycockIv)

        binding.mainKeywordrecTodaycockIv.setOnClickListener {
//            (activity as MainActivity).detailcocktail(cocktailInfoId)
            val intent = Intent(requireContext(), MenuDetailActivity::class.java)
            intent.putExtra("cocktailId",cocktailInfoId)
            startActivity(intent)
        }

        binding.mainKeywordrecTodaycockNameTv.text = koreanName
        binding.mainKeywordrecTodaycockEnnameTv.text = englishName
        binding.mainKeywordrecCurrentpageTv.text = (position+1).toString()
        binding.mainKeywordrecTotalpageTv.text = (5).toString()

        binding.mainKeywordrecTextlv.removeAllViews()
        for(i in 0 until min(cocktailKeyword.size,6)){
            val keywordtext  = TextView(context)
            keywordtext.setTextColor(resources.getColor(R.color.white))
            keywordtext.textSize = 14f
            keywordtext.setPaddingVertical(5)
            keywordtext.text = "#${cocktailKeyword[i].keywordName}"
            binding.mainKeywordrecTextlv.addView(keywordtext)
        }
    }

}