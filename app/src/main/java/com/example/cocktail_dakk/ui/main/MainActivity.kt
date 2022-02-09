package com.example.cocktail_dakk.ui.main

import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.UserInfo
import com.example.cocktail_dakk.databinding.ActivityMainBinding
import com.example.cocktail_dakk.ui.BaseActivity
import com.example.cocktail_dakk.ui.search.SearchFragment
import hearsilent.discreteslider.DiscreteSlider
import kotlin.collections.ArrayList


class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate){
    private lateinit var navHostFragment: NavHostFragment

    var gijulist = ArrayList<String>()
    var favorkeyword = ArrayList<String>()
    var dosumin:Int = 0
    var dosumax : Int = 0

    override fun initAfterBinding() {
        setBottomNavigation()
//        FilterClcikListener()
    }
    fun showbottomnavation() {
        var animation : Animation = AlphaAnimation(0f,1f);
        animation.setDuration(500)
        binding.mainBottomNavigation.animation = animation
        binding.mainBottomNavigation.visibility = View.VISIBLE
    }
    fun hidebottomnavation(){
        binding.mainBottomNavigation.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        changeSearchtab()
    }


    fun changeSearchtab(){
        var spf = getSharedPreferences("currenttab", MODE_PRIVATE)
        if (spf.getInt("currenttab",0) == 0){
            binding.mainBottomNavigation.selectedItemId = R.id.searchFragment
        }
        else if (spf.getInt("currenttab",0) == 1){
            binding.mainBottomNavigation.selectedItemId = R.id.homeFragment
        }
    }

    fun changetoSearchtab(){
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
        var spf = getSharedPreferences("currenttab", MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab", 1)
        editor.apply()
    }
}


