package com.example.cocktail_dakk.ui.temp


import android.content.Context
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail_locker
import com.example.cocktail_dakk.databinding.FragmentLockerBinding
import com.example.cocktail_dakk.ui.BaseFragment

class LockerFragment : BaseFragment<FragmentLockerBinding>(FragmentLockerBinding::inflate) {
    var dummydata = arrayListOf<Cocktail_locker>(
        Cocktail_locker("스트로베리 다이키리", "Strawberry RR", R.drawable.img_cocktail_woowoo, "딸기, 스트로, 베리"),
        Cocktail_locker("오렌지 쥬스", "Orange dummy", R.drawable.img_cocktail_b_b, "오렌지, 렌지, 전자레인지, 쥬스, 다섯개가끝"),
        Cocktail_locker("망고먹는 셜록", "mango lingo", R.drawable.search_ex1, "망고먹는, 셜록"),
    )

    override fun initAfterBinding() {
        selectCocktail(1)
    }


    private fun selectCocktail(num: Int) {
        binding.lockerCocktailLocalNameTv.text = dummydata[num].localName
        binding.lockerCocktailEnglishNameTv.text = dummydata[num].englishName
        binding.lockerCocktailImgIv.setImageResource(dummydata[num].image)

        var keywords = dummydata[num].keywords.split(",") as ArrayList<String>
        for (i in 0 until keywords.size) {
            keywords[i] = keywords[i].trim()
        }

        val l1 = binding.lockerCocktailKeywordConstLa
        l1.removeAllViews()
        for (i in 0 until keywords.size-1){
            l1.addView(createKeyword(keywords[i], 12.0f, "000000", 60))
            val vu = View(this.activity)
            var layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity,10), 0)
            layoutparam.setMargins(0,80,0,0)
            vu.layoutParams = layoutparam
            l1.addView(vu)
        }

    }


    private fun createKeyword(inputText : String, size: Float, color: String, width: Int = -1, height: Int = -1) : TextView {
        val textView = TextView(this.activity)
        textView.text = inputText
        textView.textSize = size
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setBackgroundResource(R.drawable.round_rect_sky_in_grey)
        textView.setTextColor(Color.parseColor("#$color"))
        textView.setPadding(0,DPtoPX(this.activity,2),0,DPtoPX(this.activity,2))
        val lp =
            if (width==-1 && height==-1) LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            else if (width != -1) {
                LinearLayout.LayoutParams(DPtoPX(this.activity, width), ViewGroup.LayoutParams.WRAP_CONTENT)
            } else LinearLayout.LayoutParams(DPtoPX(this.activity, width), DPtoPX(this.activity, height))
        textView.layoutParams = lp
        return textView
    }

    private fun DPtoPX(context: FragmentActivity?, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context?.resources?.displayMetrics
        ).toInt()
    }
}