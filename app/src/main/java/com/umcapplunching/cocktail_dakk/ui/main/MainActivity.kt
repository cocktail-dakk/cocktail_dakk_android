package com.umcapplunching.cocktail_dakk.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.ActivityMainBinding
import com.umcapplunching.cocktail_dakk.ui.BaseActivity
import kotlin.collections.ArrayList
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.CocktailDatabase
import com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService.*
import com.umcapplunching.cocktail_dakk.ui.mypage.MypageResettingDosuFragment
import com.umcapplunching.cocktail_dakk.ui.mypage.MypageResettingGijuFragment
import com.umcapplunching.cocktail_dakk.ui.mypage.MypageResettingKeywordFragment
import com.umcapplunching.cocktail_dakk.ui.search.searchService.*
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchView
import com.umcapplunching.cocktail_dakk.ui.start.Service.TokenResfreshView
import com.umcapplunching.cocktail_dakk.ui.start.Service.Tokenrespbody
import com.umcapplunching.cocktail_dakk.ui.start.Service.UserService
import com.umcapplunching.cocktail_dakk.utils.getrefreshtoken
import com.umcapplunching.cocktail_dakk.utils.setaccesstoken
import com.umcapplunching.cocktail_dakk.utils.setrefreshtoken
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.umcapplunching.cocktail_dakk.ui.menu_detail.DetailFragment
import com.umcapplunching.cocktail_dakk.ui.settings.SettingFragment
import com.umcapplunching.cocktail_dakk.ui.start.StartActivity
import com.umcapplunching.cocktail_dakk.ui.start.setting.StartNameActivity
import com.umcapplunching.cocktail_dakk.ui.start.splash.SplashActivity
import com.umcapplunching.cocktail_dakk.utils.gso
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlin.math.abs

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    TokenResfreshView, OnConnectionFailedListener,
    SearchView, GoogleApiClient.OnConnectionFailedListener {
    private lateinit var navHostFragment: NavHostFragment
    val detailService = DetailService()
    val searchService = SearchService()

    lateinit var CocktailDb: CocktailDatabase
    lateinit var mGoogleApiClient : GoogleApiClient
    // 유저 변수에 저장 할 것
    private var mypageDosu: Int = 0
    private var mypageTempDosu: Int = 0
    private var mypageGijulist = ArrayList<String>()
    private var mypageTempGijulist = ArrayList<String>()
    private var mypageKeywords = ArrayList<String>()
    private var mypageTempKeywords = ArrayList<String>()
    private val threeFragments = arrayListOf<Fragment>(
        MypageResettingDosuFragment(),
        MypageResettingGijuFragment(),
        MypageResettingKeywordFragment()
    )
    var cocktailInfoId: Int = 0
    var backflag = false

    private var backKeyPressedTime: Long = 0
    lateinit var toast: Toast

    private var mypageReStatus: Boolean = false// false:기본, true:mypage닉네임or정보 설정창on상태

    private var userService = UserService()

    fun clearThree() {
        for (i in 0 until threeFragments.size) {
            supportFragmentManager.beginTransaction().remove(threeFragments[i])
                .commitAllowingStateLoss()
        }
    }

    fun getMypageDosu(): Int = mypageDosu
    fun setMypageDosu(dosu: Int) {
        mypageDosu = dosu
    }

    fun getMypageTempDosu(): Int = mypageTempDosu
    fun setMypageTempDosu(dosu: Int) {
        mypageTempDosu = dosu
    }

    // arrayList 들은 get 해올때 반드시 addall 등 "깊은 복사"를 할것! deep copy 하지 않으면 공통 참조가 됨
    fun getMypageGijulist(): ArrayList<String> = mypageGijulist
    fun setMypageGijulist(gijulist: ArrayList<String>) {
        mypageGijulist = gijulist
    }

    fun getMypageTempGijulist(): ArrayList<String> = mypageTempGijulist
    fun setMypageTempGijulist(gijulist: ArrayList<String>) {
        mypageTempGijulist = gijulist
    }

    fun getMypageKeywords(): ArrayList<String> = mypageKeywords
    fun setMypageKeywords(keywords: ArrayList<String>) {
        mypageKeywords = keywords
    }

    fun getMypageTempKeywords(): ArrayList<String> = mypageTempKeywords
    fun setMypageTempKeywords(keywords: ArrayList<String>) {
        mypageTempKeywords = keywords
    }

    fun showKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        view.requestFocus()
        inputMethodManager.showSoftInput(view, 0)
    }

    @SuppressLint("ResourceType")
    override fun initAfterBinding() {
        setBottomNavigation()
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(
                this,this
            )
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        CocktailDb = CocktailDatabase.getInstance(this)!!
        searchService.setsearchView(this)
        userService.settokenRefreshView(this)

    }

    fun showbottomnavation() {
//        val animation: Animation = AlphaAnimation(0f, 1f)
//        animation.setDuration(500)
//        binding.mainBottomNavigation.animation = animation
        binding.mainBottomNavigation.visibility = View.VISIBLE
    }

    fun hidebottomnavation() {
        binding.mainBottomNavigation.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        //showbottomnavation()
        changeSearchtab()
    }

    //onResume됬을 때 fragment를 어디로 할지.
    fun changeSearchtab() {
        val spf = getSharedPreferences("currenttab", MODE_PRIVATE)
        if (spf.getInt("currenttab", -1) == 0) {
            binding.mainBottomNavigation.selectedItemId = R.id.searchFragment
        }
        else if (spf.getInt("currenttab", -1) == 2) {
            val spf_locker = this.getSharedPreferences("lockerflag", MODE_PRIVATE)
            if (spf_locker.getInt("lockerflag", 0) == 1) {
                binding.mainBottomNavigation.selectedItemId = R.id.lockerFragment
            }
        }
        else if (spf.getInt("currenttab", -1) == 3) {
            binding.mainBottomNavigation.selectedItemId = R.id.mypageFragment
        }
//        else if (spf.getInt("currenttab", 0) == 1) {
//            binding.mainBottomNavigation.selectedItemId = R.id.homeFragment
//        }
    }

    fun changetoSearchtab() {
        binding.mainBottomNavigation.selectedItemId = R.id.searchFragment
    }

    private fun setBottomNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()
        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null
        binding.navHostFragmentContainer.isSaveEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        SetCurrentpageMain()
    }

    private fun SetCurrentpageMain() {
        val spf = getSharedPreferences("currenttab", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab", 1)
        editor.apply()
    }

    fun setMypageReStatus(restatus: Boolean) {
        mypageReStatus = restatus
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.navDetailFragmentContainer.visibility=View.GONE
        if (!mypageReStatus) {
            if (backflag) {
                DetailBackArrow()
                return
            }
            if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
                backKeyPressedTime = System.currentTimeMillis()
                Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()
                return
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
                finish()
                toast.cancel()
            }
        } else {
            super.onBackPressed() //mypage fragment 에서 설정창 incisible 하게
            mypageReStatus = false
        }
    }

    //칵테일 디테일
    fun detailcocktail(id: Int) {
        backflag = true
        binding.mainBottomNavigation.visibility = View.GONE
        binding.navDetailFragmentContainer.visibility = View.VISIBLE
        val animation: Animation = AlphaAnimation(0f, 1f)
        animation.setDuration(300)
        binding.navDetailFragmentContainer.animation = animation

        supportFragmentManager.beginTransaction().replace(
            R.id.nav_detail_fragment_container,
            DetailFragment().apply {
                Bundle().apply {
                    putString("CocktailId",id.toString())
                    putString("DetailMethod","Main")
                }.also { arguments = it }
            }
        ).commit()
    }

    fun changesettingtab(){
        binding.mainBottomNavigation.visibility = View.GONE
        binding.navDetailFragmentContainer.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().replace(
            R.id.nav_detail_fragment_container,
            SettingFragment().apply {
            }
        ).commit()
    }

    fun logout(){


        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
            startActivityWithClear(
                SplashActivity::class.java
            )
        }
    }

    fun TokenrefreshInMain() {
        userService.TokenRefresh(getrefreshtoken(this))
    }

    fun DetailBackArrow() {
        showbottomnavation()
        changeSearchtab()
        backflag = false
        binding.navDetailFragmentContainer.visibility = View.GONE
    }

    override fun onTokenRefreshLoading() {
    }

    override fun onTokenRefreshSuccess(tokenSigninbody: Tokenrespbody) {
        setaccesstoken(this, tokenSigninbody.token)
        setrefreshtoken(this, tokenSigninbody.refreshToken)
        onStart()
    }

    override fun onTokenRefreshFailure(code: Int, message: String) {
    }

    override fun onSearchLoading() {
    }

    override fun onSearchSuccess(searchresult: SearchResult) {
    }

    override fun onSearchFailure(code: Int, message: String) {
        this.runOnUiThread(object : Runnable {
            override fun run() {
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                if (code == 5000) {
                    userService.TokenRefresh(getrefreshtoken(this@MainActivity))
                }
            }
        })
    }

    override fun onPagingLoading() {
        TODO("Not yet implemented")
    }

    override fun onPagingSuccess(searchresult: SearchResult) {
        TODO("Not yet implemented")
    }

    override fun onPagingFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }
}
