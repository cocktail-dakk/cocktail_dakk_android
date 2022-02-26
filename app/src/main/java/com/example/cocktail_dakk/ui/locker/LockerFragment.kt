package com.example.cocktail_dakk.ui.locker


import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail_locker
import com.example.cocktail_dakk.databinding.FragmentLockerBinding
import com.example.cocktail_dakk.ui.BaseFragment

class LockerFragment : BaseFragment<FragmentLockerBinding>(FragmentLockerBinding::inflate) {
    var dummydata = arrayListOf(
        Cocktail_locker("스트로베리 다이키리", "Strawberry RR", R.drawable.img_cocktail_woowoo, "딸기, 스트로, 베리"),
        Cocktail_locker("오렌지 쥬스", "Orange dummy", R.drawable.img_cocktail_b_b, "오렌지, 렌지, 전자레인지, 쥬스, 다섯개가끝"),
        Cocktail_locker("망고먹는 셜록", "mango lingo", R.drawable.img_cocktail_brandysour, "망고먹는, 셜록"),
        Cocktail_locker("더미더미끝", "dummy mymy", R.drawable.img_cocktail_alaskaicedtea, "이사하고, 자취방때메, 너무바빠, 죽겠다어우"),
    )

    override fun initAfterBinding() {
        // 더미데이터랑 Adapter 연결
        val cocktailRecyclerViewAdapter = LockerRVAdapter(dummydata)
        // 리사이클러뷰에 어댑터를 연결
        binding.lockerCocktailListRv.adapter = cocktailRecyclerViewAdapter
        selectCocktailByCocktail(dummydata[0])

        cocktailRecyclerViewAdapter.setMyItemClickListener(object : LockerRVAdapter.MyItemClickListener{
            override fun onItemClick(cocktail: Cocktail_locker, position: Int) {
                cocktailRecyclerViewAdapter.changeSelcetedPosition(position)
                selectCocktailByCocktail(cocktail)
            }
        })

    }

    private fun selectCocktailByCocktail(cocktail: Cocktail_locker) {
        binding.lockerCocktailLocalNameTv.text = cocktail.localName
        binding.lockerCocktailEnglishNameTv.text = cocktail.englishName
        binding.lockerCocktailImgIv.setImageResource(cocktail.image)

        var keywords = cocktail.keywords.split(",") as ArrayList<String>
        for (i in 0 until keywords.size) {
            keywords[i] = keywords[i].trim()
        }

        val sv = binding.lockerCocktailKeywordSv
        val l1 = binding.lockerCocktailKeywordLinearLa
        l1.removeAllViews()
        for (i in 0 until keywords.size){
            l1.addView(createKeyword(keywords[i], 12.0f, "000000", 60))
            if (i==keywords.size-1) {
                break
            }
            val vu = View(this.activity)
            var layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity,10), 0)
            layoutparam.setMargins(0,80,0,0)
            vu.layoutParams = layoutparam
            l1.addView(vu)
        }

        // 스크롤 안되는 문제 해결할것! todo
//        sv.removeAllViews()
//        sv.addView(l1)
    }

    private fun createKeyword(inputText : String, size: Float, color: String, width: Int = -1, height: Int = -1) : TextView {
        val textView = TextView(this.activity)
        textView.text = inputText
        textView.textSize = size
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setBackgroundResource(R.drawable.round_rect_grey_in_sky)
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