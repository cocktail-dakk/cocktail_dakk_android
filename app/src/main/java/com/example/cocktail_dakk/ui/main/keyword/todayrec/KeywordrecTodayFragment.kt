package com.example.cocktail_dakk.ui.main.keyword.todayrec

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentKeywordrecTodayBinding
import com.example.cocktail_dakk.ui.BaseFragment
//import com.example.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService.Keyword
import com.example.cocktail_dakk.ui.search.searchService.Keyword
import com.tbuonomo.viewpagerdotsindicator.setPaddingVertical

//import com.example.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService.CocktailKeyword

class KeywordrecTodayFragment(val position : Int,val cocktailInfoId : Int, val englishName : String, val koreanName : String, val cocktailKeyword : List<Keyword>,
                              val recommendImageURL : String) : BaseFragment<FragmentKeywordrecTodayBinding>(FragmentKeywordrecTodayBinding::inflate) {
    override fun initAfterBinding() {
        Glide.with(this)
            .load(recommendImageURL)
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.img_cocktail_alaskaicedtea_dailyrec)
            .into(binding.mainKeywordrecTodaycockIv)

        binding.mainKeywordrecTodaycockNameTv.text = koreanName
        binding.mainKeywordrecTodaycockEnnameTv.setText(englishName)
        binding.mainKeywordrecCurrentpageTv.setText((position+1).toString())
        binding.mainKeywordrecTotalpageTv.setText((4).toString())
//        binding.mainKeywordrecTodaycockTagTv1.text = cocktailKeyword[0].keywordName

        for(i in cocktailKeyword){
            var keywordtext  = TextView(context)
            keywordtext.setTextColor(resources.getColor(R.color.white))
            keywordtext.textSize = 14f
            keywordtext.setPaddingVertical(5)
            keywordtext.setText("#"+i.keywordName)
            binding.mainKeywordrecTextlv.addView(keywordtext)
        }
    }

//    private fun createTextView(inputText : String, size: Float, color: String, width: Int = -1, height: Int = -1) : TextView {
//        val textView = TextView(context)
//        textView.text = inputText
//        textView.textSize = size
//        textView.setTextColor(Color.parseColor("#$color"))
//        val lp =
//            if (width==-1 && height==-1) LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//            else LinearLayout.LayoutParams(DPtoPX(context, width), DPtoPX(this, height))
//        textView.layoutParams = lp
//        return textView
//    }

//    private fun DPtoPX(context: Context, dp: Int): Int {
//        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
//    }

}