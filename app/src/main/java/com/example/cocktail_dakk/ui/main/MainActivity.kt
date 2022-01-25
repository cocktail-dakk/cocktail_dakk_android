//package com.cock_tail.test_xml.ui.main
package com.example.cocktail_dakk.ui.main

import android.app.Activity
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.View.inflate
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.ActivityMainBinding
import com.example.cocktail_dakk.ui.BaseActivity


class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navHostFragment: NavHostFragment


    override fun initAfterBinding() {

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()
        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null
//        app:labelVisibilityMode="unlabeled" 제목 가리기

    }

    fun ShowFilter(isshow : Boolean){
        if(isshow){
            binding.mainBottomNavigation.visibility = View.GONE
            var animation : Animation = TranslateAnimation(0f,0f,150f, 0f);
            animation.setDuration(200)
            binding.mainFilterBackLayout.animation = animation
            binding.mainFilterBackLayout.visibility = View.VISIBLE
            binding.mainFilterBackgroundcoverIv.visibility = View.VISIBLE
        }
        else{
            var animation : Animation = AlphaAnimation(0f,1f);
            animation.setDuration(500)
            binding.mainBottomNavigation.animation = animation
            binding.mainFilterBackLayout.animation = animation
            binding.mainBottomNavigation.visibility = View.VISIBLE

            animation  = TranslateAnimation(0f,0f,0f, 1500f);
            animation.setDuration(500)
            binding.mainFilterBackLayout.animation = animation
            binding.mainFilterBackLayout.visibility = View.GONE
            binding.mainFilterBackgroundcoverIv.visibility = View.GONE

        }
    }


}


