package com.umcapplunching.cocktail_dakk.ui.main

import android.app.Activity
import android.content.SharedPreferences
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.ActivityMainBinding
import kotlin.collections.ArrayList
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService.*
import com.umcapplunching.cocktail_dakk.ui.search.searchService.*
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchView
import com.umcapplunching.cocktail_dakk.ui.start.Service.TokenResfreshView
import com.umcapplunching.cocktail_dakk.ui.start.Service.Tokenrespbody
import com.umcapplunching.cocktail_dakk.ui.start.Service.UserService
import com.umcapplunching.cocktail_dakk.ui.search.SearchCocktailViewModel
import com.umcapplunching.cocktail_dakk.utils.*
import android.content.Intent
import android.util.Log
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import com.umcapplunching.cocktail_dakk.ui.BaseActivityByDataBinding

class MainActivity : BaseActivityByDataBinding<ActivityMainBinding>(R.layout.activity_main),
    TokenResfreshView, OnConnectionFailedListener {
    private lateinit var navHostFragment: NavHostFragment
    var cocktailInfoId: Int = 0
    private var backKeyPressedTime: Long = 0
    private var userService = UserService()

    private lateinit var searchCocktailViewModel : SearchCocktailViewModel
    private lateinit var mainViewModel : MainViewModel

    override fun initViewModel() {
        setBottomNavigation()
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        searchCocktailViewModel = ViewModelProvider(this).get(SearchCocktailViewModel::class.java)
        userService.settokenRefreshView(this)
    }

    override fun onResume() {
        super.onResume()
        if(intent.hasExtra("searchStr")){
            binding.mainBottomNavigation.selectedItemId = R.id.searchFragment
            searchCocktailViewModel.updateSearchMode(SearchCocktailViewModel.SearchMode.SEARCH)
            searchCocktailViewModel.setSearchStr(intent.getStringExtra("searchStr").toString())
            intent.removeExtra("searchStr")
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0,0)
    }
    
    // SingTask Activity에서 인텐트 가져오기
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    fun changetoSearchtab() {
        binding.mainBottomNavigation.selectedItemId = R.id.searchFragment
    }

    private fun setBottomNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment_container)
        navController.navigatorProvider.addNavigator(navigator)
        navController.setGraph(R.navigation.navigation)
        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null
        binding.navHostFragmentContainer.isSaveEnabled = false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.navDetailFragmentContainer.visibility=View.GONE
            if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish()
//                toast.cancel()
        }
    }

    fun TokenrefreshInMain() {
        userService.TokenRefresh(getrefreshtoken(this))
    }

    fun reStartActivity(){
        finish()
        val intent = Intent(CocktailDakkApplication.getInstance(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
    }

    override fun onTokenRefreshSuccess(tokenSigninbody: Tokenrespbody) {
        CocktailDakkApplication.AccessToken = tokenSigninbody.token
        CocktailDakkApplication.RefreshToken = tokenSigninbody.refreshToken
        setrefreshtoken(this, tokenSigninbody.refreshToken)
    }

    override fun onTokenRefreshFailure(code: Int, message: String) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }
}
