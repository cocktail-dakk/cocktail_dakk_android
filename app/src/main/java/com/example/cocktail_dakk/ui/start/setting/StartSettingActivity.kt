package com.example.cocktail_dakk.ui.start.setting

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cocktail_dakk.data.entities.UserInfo
import com.example.cocktail_dakk.databinding.ActivityStartSettingBinding
import com.example.cocktail_dakk.ui.BaseActivity
import com.example.cocktail_dakk.ui.main.MainActivity

import com.example.cocktail_dakk.ui.main.adapter.StartSettingViewpagerAdapter
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecService
import com.example.cocktail_dakk.ui.start.Service.*
import com.example.cocktail_dakk.ui.start.setting.fragment.*
import com.google.gson.Gson

class StartSettingActivity : BaseActivity<ActivityStartSettingBinding>(ActivityStartSettingBinding::inflate),SignupView,
    AutoLoginView {

    private lateinit var viewPager: ViewPager2
    lateinit var nickname : String
    lateinit var userRequest : UserRequest
    val userService = UserService()
    var instantId = ""
//    val mainrecService = MainrecService()
//    mainrecService.setmainrecView(this)
//    mainrecService.mainRec("1234")

    var dosumin : Int = 0
    var dosumax : Int = 0

    override fun initAfterBinding() {

        userService.setsignupView(this)

        userRequest = UserRequest(0,"devicenum","nickname",20,"M",
        0,"탄산,달달한,","달달한,")

        //인스턴트 ID
        var spf = getSharedPreferences("InstanceID", AppCompatActivity.MODE_PRIVATE)
        userRequest.deviceNum = spf.getString("InstanceID", " ").toString()

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
        binding.startSettingDotback.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
            }
        })

    }



    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    fun Nextpage(){
        viewPager.currentItem = viewPager.currentItem + 1
    }

    fun Undopage(){
        viewPager.currentItem = viewPager.currentItem - 1
    }

    fun setdosu(dmin : Int){
        dosumin = dmin
//        dosumax = dmax
        userRequest.alcoholLevel = dmin
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
        var gson = Gson()
        userService.signup(userRequest)

    }

    //회원가입
    override fun onSignupLoading() {
    }

    override fun onSignupSuccess(userbody: Userbody) {
        var spf = getSharedPreferences("InstanceID", MODE_PRIVATE)
        instantId = spf!!.getString("InstanceID"," ")!!
        val autologinservce= UserService()
        autologinservce.setautologinView(this)
        autologinservce.autologin(instantId)
    }

    override fun onSignupFailure(code: Int, message: String) {
    }

    //로그인
    override fun onLoginLoading() {
    }

    override fun onLoginSuccess(autologinbody: Autologinbody) {
        initUser(autologinbody)
        //현재탭 메인으로 설정
        var spf = getSharedPreferences("currenttab", MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab", 1)
        editor.apply()

        //검색어 비우기
        spf = getSharedPreferences("searchstr", MODE_PRIVATE)
        editor.putString("searchstr", " ")
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onLoginFailure(code: Int, message: String) {
        Toast.makeText(this,"회원가입에 실패했어요.",Toast.LENGTH_SHORT)
        startActivityWithClear(StartSettingActivity::class.java)
    }

    private fun initUser(autologinbody: Autologinbody) {
        var gijulist = ""
        for (i in autologinbody.userDrinks) {
            gijulist += i.drinkName + ","
        }
        var keywrodlist = ""
        for (i in autologinbody.userKeywords) {
            keywrodlist += i.keywordName + ","
        }

        var userinfo = UserInfo(
            autologinbody.age, autologinbody.alcoholLevel, instantId,
            autologinbody.nickname, autologinbody.sex, gijulist, keywrodlist
        )
        val gson = Gson()
        var spf = getSharedPreferences("UserInfo", MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putString("UserInfo", gson.toJson(userinfo))
        editor.apply()
    }

}