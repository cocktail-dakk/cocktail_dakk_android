package com.example.cocktail_dakk.ui.mypage

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.User
import com.example.cocktail_dakk.databinding.FragmentMypageBinding
import com.example.cocktail_dakk.ui.BaseFragment

class MypageFragment:BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate) {

    private var user = User("셜록닉네임", "소주 20병", "", "가벼운, 알록달록, 간단한")

    override fun initAfterBinding() {
        initUser(user)
    }

    private fun initUser(user: User){

        binding.mypageNicknameTv.text = when (user.nickname) {
            "" -> "이름 없음"
            else -> user.nickname
        }

        binding.mypageLevelContextTv.text = when (user.drinkingLevel) {
            "" -> "주량을 몰라요"
            else -> user.drinkingLevel
        }

        binding.mypageBaseContextTv.text = when (user.preferBase) {
            "" -> "마셔본 적 없어요"
            else -> user.preferBase
        }

        val keywords = user.keywords.split(",") as ArrayList<String>
        for (i in 0 until keywords.size) {
            keywords[i] = keywords[i].trim()
        }

        val l1 = binding.mypageKeywordContextLa

        for (i in 0 until keywords.size){
            l1.addView(createKeyword(keywords[i], 15.0f, "000000", 70))
            val vu = View(this.activity)
            vu.layoutParams = LinearLayout.LayoutParams(DPtoPX(this.activity,10), 0)
            l1.addView(vu)
        }

    }

    private fun createKeyword(inputText : String, size: Float, color: String, width: Int) : TextView {
        val textView = TextView(this.activity)
        textView.text = inputText
        textView.textSize = size
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setBackgroundResource(R.drawable.round_rect_white_in_sky)
        textView.setTextColor(Color.parseColor("#$color"))
        textView.setPadding(0,DPtoPX(this.activity,4),0,DPtoPX(this.activity,4))
        val lp = LinearLayout.LayoutParams(DPtoPX(this.activity, width), ViewGroup.LayoutParams.WRAP_CONTENT)
        textView.layoutParams = lp
        return textView
    }

    private fun DPtoPX(context: FragmentActivity?, dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context?.resources?.displayMetrics).toInt()
    }
}