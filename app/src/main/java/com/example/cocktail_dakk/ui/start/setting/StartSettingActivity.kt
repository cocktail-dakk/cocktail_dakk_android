package com.example.cocktail_dakk.ui.start.setting

import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cocktail_dakk.databinding.ActivityStartSettingBinding
import com.example.cocktail_dakk.ui.BaseActivity
import com.example.cocktail_dakk.ui.main.MainActivity

import com.example.cocktail_dakk.ui.main.adapter.StartSettingViewpagerAdapter
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecService
import com.example.cocktail_dakk.ui.start.Service.SignupView
import com.example.cocktail_dakk.ui.start.Service.UserRequest
import com.example.cocktail_dakk.ui.start.Service.UserService
import com.example.cocktail_dakk.ui.start.Service.Userbody
import com.example.cocktail_dakk.ui.start.setting.fragment.*
import com.google.gson.Gson

class StartSettingActivity : BaseActivity<ActivityStartSettingBinding>(ActivityStartSettingBinding::inflate),SignupView {

    private lateinit var viewPager: ViewPager2
    lateinit var nickname : String
    lateinit var userRequest : UserRequest
    val userService = UserService()

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

    fun setdosu(dmin : Int, dmax : Int){
        dosumin = dmin
        dosumax = dmax
        userRequest.alcoholLevel = dosumax
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
//        Log.d("datavalutetest",gson.toJson(userRequest))
        startActivityWithClear(MainActivity::class.java)
    }

    //로그인 뷰
    override fun onSignupLoading() {
    }

    override fun onSignupSuccess(userbody: Userbody) {
    }

    override fun onSignupFailure(code: Int, message: String) {
    }


}