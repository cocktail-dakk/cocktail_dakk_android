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
import com.example.cocktail_dakk.data.entities.UserInfo
import com.example.cocktail_dakk.data.entities.getUser
import com.example.cocktail_dakk.databinding.FragmentMypageBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.utils.getReposit

class MypageFragment:BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate) {

//    private var user = User("셜록닉네임", "소주 20병", "",
//        "알록달록, 간단한,가벼운, 알록달록, 간단한,간단한,가벼운, 알록달록, 간단한,가벼운, 알록달록, 간단한," +
//                "알록달록, 간단한,가벼운, 알록달록, 간단한,간단한,가벼운, 알록달록, 간단한,가벼운, 알록달록, 간단한," +
//                "알록달록, 간단한,가벼운, 알록달록, 간단한,간단한,가벼운, 알록달록, 간단한,가벼운, 알록달록, 간단한")
    private lateinit var userInfo:UserInfo

    override fun initAfterBinding() {
        userInfo = getUser(requireContext())
        initUser(userInfo)
    }

    private fun initUser(user: UserInfo){

        binding.mypageNicknameTv.text = when (user.nickname) {
            "" -> "이름 없음"
            else -> user.nickname
        }

        binding.mypageLevelContextTv.text = when (user.alcoholLevel) {
            0 -> "무알콜 선호자"
            else -> user.alcoholLevel.toString() + "도"
        }


        if (userInfo.sex.equals("M")){
            binding.mypageProfileIv.setImageResource(R.drawable.mypage_profile)
        }
        else{
            binding.mypageProfileIv.setImageResource(R.drawable.img_mypage_girl)
        }

        val gijulist = user.userDrinks.split(",") as ArrayList<String>
        for (i in 0 until gijulist.size) {
            gijulist[i] = gijulist[i].trim()
        }

        val gijufa = binding.mypageGijuContextFa

        for (i in 0 until gijulist.size-1){
            gijufa.addView(createKeyword(gijulist[i], 15.0f, "000000", 70))
            val vu = View(this.activity)
            var layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity,10), 0)
            layoutparam.setMargins(0,100,0,0)
            vu.layoutParams = layoutparam
            gijufa.addView(vu)
        }

        val keywords = user.userKeywords.split(",") as ArrayList<String>
        for (i in 0 until keywords.size) {
            keywords[i] = keywords[i].trim()
        }

        val l1 = binding.mypageKeywordContextFa

        for (i in 0 until keywords.size-1){
            l1.addView(createKeyword(keywords[i], 15.0f, "000000", 70))
            val vu = View(this.activity)
            var layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity,10), 0)
            layoutparam.setMargins(0,100,0,0)
            vu.layoutParams = layoutparam
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