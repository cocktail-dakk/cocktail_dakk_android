package com.umcapplunching.cocktail_dakk.ui.start.setting

import android.content.Intent
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.umcapplunching.cocktail_dakk.data.entities.UserInfo_forApp
import com.umcapplunching.cocktail_dakk.databinding.ActivityStartSettingBinding
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.main.adapter.StartSettingViewpagerAdapter
import com.umcapplunching.cocktail_dakk.ui.start.Service.*
import com.umcapplunching.cocktail_dakk.ui.start.setting.fragment.*
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.ui.BaseActivityByDataBinding

class StartSettingActivity : BaseActivityByDataBinding<ActivityStartSettingBinding>(R.layout.activity_start_setting),SignupView{

    private lateinit var viewPager: ViewPager2
    lateinit var nickname : String
    lateinit var userRequest : UserRequest
    val userService = UserService()

    var userdosu : Int = 0

    override fun initView() {
        userService.setsignupView(this)
        // Dummy 데이터 예제
        userRequest = UserRequest("nickname",20,"M",
        0,"탄산,달달한,","달달한,")

        //닉네임
        userRequest.nickname = intent.getStringExtra("nickname").toString().trim()
        nickname = intent.getStringExtra("nickname").toString().trim()

        val adapter = StartSettingViewpagerAdapter(this)
        adapter.addFragmentInStartSetting(StartAStartFragment(nickname))
        adapter.addFragmentInStartSetting(StartBAgeFragment())
        adapter.addFragmentInStartSetting(StartCGenderFragment())
        adapter.addFragmentInStartSetting(StartDDrinkcapFragment())
        adapter.addFragmentInStartSetting(StartELikeFragment())
        adapter.addFragmentInStartSetting(StartFKeywordFragment())

        viewPager = binding.startSettingVp

        //손으로 움직이는거 막기
        viewPager.isUserInputEnabled = false
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val dotsIndicator = binding.startSettingDot
        dotsIndicator.setViewPager2(viewPager)

        //닷인디케이터 터치안되게 막아버림
        binding.startSettingDotback.setOnClickListener { }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    fun Nextpage(){
        viewPager.currentItem = viewPager.currentItem + 1
    }

    fun Undopage(){
        viewPager.currentItem = viewPager.currentItem - 1
    }

    fun setdosu(dosu : Int){
        userdosu = dosu
        userRequest.alcoholLevel = dosu
    }

    fun setAge(age : Int){
        userRequest.age = age
    }

    fun setGender(gender : String){
        userRequest.sex = gender
    }

    fun setBasegiju(giju : String){
        userRequest.favouritesDrinks = giju
    }

    fun setKeyword(keyword : String){
        userRequest.favouritesKeywords = keyword
    }

    fun signupfinish(){
        userService.signup(userRequest, CocktailDakkApplication.AccessToken)
    }

    override fun onSignupSuccess(userbody: Userbody) {
        initUser(userbody)
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
    }

    override fun onSignupFailure(code: Int, message: String) {
        //회원가입 오류
        Toast.makeText(this,"인터넷 연결을 확인해주세요",Toast.LENGTH_SHORT).show()
    }

    private fun initUser(userbody: Userbody) {
        // 유저 정보 변환
        var gijulist = ""
        for (i in userbody.userDrinks) {
            gijulist += i.drinkName + ","
        }
        var keywrodlist = ""
        for (i in userbody.userKeywords) {
            keywrodlist += i.keywordName + ","
        }
        val userinfo = UserInfo_forApp(
            userbody.age, userbody.alcoholLevel, userbody.nickname, userbody.sex, gijulist, keywrodlist
        )
        CocktailDakkApplication.userInfoForApp = userinfo

    }

}