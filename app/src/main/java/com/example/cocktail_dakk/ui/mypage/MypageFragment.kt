package com.example.cocktail_dakk.ui.mypage

import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.User
import com.example.cocktail_dakk.data.entities.UserInfo
import com.example.cocktail_dakk.data.entities.getUser
import com.example.cocktail_dakk.databinding.FragmentMypageBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.MainActivity
import com.example.cocktail_dakk.ui.main.adapter.MainViewpagerAdapter
import com.example.cocktail_dakk.ui.main.adapter.MypageViewpagerAdapter
import com.example.cocktail_dakk.utils.getReposit
import com.google.android.material.tabs.TabLayoutMediator

class MypageFragment:BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate) {

//    private var user = User("셜록닉네임", "소주 20병", "",
//        "알록달록, 간단한,가벼운, 알록달록, 간단한,간단한,가벼운, 알록달록, 간단한,가벼운, 알록달록, 간단한," +
//                "알록달록, 간단한,가벼운, 알록달록, 간단한,간단한,가벼운, 알록달록, 간단한,가벼운, 알록달록, 간단한," +
//                "알록달록, 간단한,가벼운, 알록달록, 간단한,간단한,가벼운, 알록달록, 간단한,가벼운, 알록달록, 간단한")
    private lateinit var userInfo: UserInfo
    private val information = arrayListOf("주량", "기주", "키워드")
    private lateinit var adapter: MypageViewpagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var animation2: Animation

    override fun initAfterBinding() {
        animation2 = AlphaAnimation(0f,1f);
        animation2.duration = 300

        userInfo = getUser(requireContext())
        initUser(userInfo)

        adapter = MypageViewpagerAdapter(this)
        viewPager = binding.mypageResettingViewpagerVp
        viewPager.adapter = adapter

//        binding.mypageResettingViewpagerVp.isUserInputEnabled = false // 스와이프 못하게
        TabLayoutMediator(binding.mypageResettingTablayoutTl, binding.mypageResettingViewpagerVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        initClicker()

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

        // mainactivity 에도 mypage 변수들 변경
        (activity as MainActivity)!!.setMypageDosu(userInfo.alcoholLevel)
        (activity as MainActivity)!!.setMypageGijulist(gijulist)
        (activity as MainActivity)!!.setMypageKeywords(keywords)

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

    private fun initClicker(){

        // ***** 닉네임 변경 시작

        binding.mypageNicknameResetIv.setOnClickListener(){
            binding.mypageRenameEditEt.text = null
            binding.mypageRenameBackgroundLa.visibility = View.VISIBLE
            binding.mypageRenameBackgroundLa.animation = animation2
        }
        binding.mypageNicknameResetTv.setOnClickListener(){
            binding.mypageRenameEditEt.text = null
            binding.mypageRenameBackgroundLa.visibility = View.VISIBLE
            binding.mypageRenameBackgroundLa.animation = animation2
        }

        binding.mypageRenameWhiteboardLa.setOnClickListener(){
            // 아무것도 안함. 배경 클릭과의 대비를 두기 위한 코드. 지우지 말것!
        }

        binding.mypageRenameBackgroundLa.setOnClickListener(){
            binding.mypageRenameBackgroundLa.visibility = View.GONE
        }
        binding.mypageRenameExitIv.setOnClickListener(){
            binding.mypageRenameBackgroundLa.visibility = View.GONE
        }


        binding.mypageRenameOkOnTv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (binding.mypageRenameEditEt.text.toString().replace(" ", "").equals("")) {
                    binding.mypageRenameNickcheckTv.visibility = View.VISIBLE
                    var animjindong: Animation = AnimationUtils
                        .loadAnimation(activity, R.anim.jindong)
                    binding.mypageRenameNickcheckTv.startAnimation(animjindong)
                }
                else{
                    binding.mypageRenameBackgroundLa.visibility = View.GONE
                    val reNickName = binding.mypageRenameEditEt.text
                    binding.mypageNicknameTv.text = reNickName
                    makeTextInput("닉네임을 변경했습니다.")
                    // 서버로도 데이터 보낼것!!!!!


                    binding.mypageRenameEditEt.text = null
                    binding.mypageRenameNickcheckTv.visibility = View.INVISIBLE
                }
            }
        })

        binding.mypageRenameEditEt.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.mypageRenameEditEt.text.toString().replace(" ", "").equals("")) {
                    binding.mypageRenameNickcheckTv.visibility = View.VISIBLE
                    var animjindong: Animation = AnimationUtils
                        .loadAnimation(activity, R.anim.jindong)
                    binding.mypageRenameNickcheckTv.startAnimation(animjindong)
                }
                else{
                    binding.mypageRenameBackgroundLa.visibility = View.GONE
                    val reNickName = binding.mypageRenameEditEt.text
                    binding.mypageNicknameTv.text = reNickName
                    makeTextInput("닉네임을 변경했습니다.")

                    // 서버로도 데이터 보낼것!!!!!

                    binding.mypageRenameEditEt.text = null
                    binding.mypageRenameNickcheckTv.visibility = View.INVISIBLE
                }
                handled = true
            }
            handled
        }

        // ***** 닉네임 변경 끝

        // ***** resetting

        binding.mypageLevelResetIv.setOnClickListener(){
            changeResettingFragmentByPosition(0)
        }
        binding.mypageLevelResetTv.setOnClickListener(){
            changeResettingFragmentByPosition(0)
        }

        binding.mypageBaseResetIv.setOnClickListener(){
            changeResettingFragmentByPosition(1)
        }
        binding.mypageBaseResetTv.setOnClickListener(){
            changeResettingFragmentByPosition(1)
        }

        binding.mypageKeywordResetIv.setOnClickListener(){
            changeResettingFragmentByPosition(2)
        }
        binding.mypageKeywordResetTv.setOnClickListener(){
            changeResettingFragmentByPosition(2)
        }

        binding.mypageResettingWhiteboardLa.setOnClickListener(){
            // 아무것도 안함. 배경 클릭과의 대비를 두기 위한 코드. 지우지 말것!
        }

        binding.mypageResettingBackgroundLa.setOnClickListener(){
            binding.mypageResettingBackgroundLa.visibility = View.GONE
//            binding.mypageResettingViewpagerVp.adapter(ni)

        }
        binding.mypageResettingExitIv.setOnClickListener(){
            binding.mypageResettingBackgroundLa.visibility = View.GONE
        }

        binding.mypageResettingOkOnTv.setOnClickListener(){
            binding.mypageResettingBackgroundLa.visibility = View.GONE
            // 데이터들 변경, 서버에 데이터 전송!!
            binding.mypageLevelContextTv.text = (activity as MainActivity)!!.getMypageDosu().toString()+"도"
            makeTextInput("변경사항을 저장했습니다.")
        }

    }

    private fun makeTextInput(inputText: String){
        Toast.makeText(this.activity, inputText, Toast.LENGTH_SHORT).show()
    }

    private fun changeResettingFragmentByPosition(position: Int){
        adapter = MypageViewpagerAdapter(this)
        viewPager.adapter = adapter

        binding.mypageResettingBackgroundLa.visibility = View.VISIBLE
        binding.mypageResettingBackgroundLa.animation = animation2
        viewPager.postDelayed({ viewPager.currentItem = position }, 10)
        adapter.notifyDataSetChanged()
    }

}