package com.example.cocktail_dakk.ui.start.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktail_dakk.data.entities.UserInfo
import com.example.cocktail_dakk.databinding.ActivitySplashBinding
import com.example.cocktail_dakk.ui.main.MainActivity
import com.example.cocktail_dakk.ui.start.Service.AutoLoginView
import com.example.cocktail_dakk.ui.start.Service.Autologinbody
import com.example.cocktail_dakk.ui.start.Service.UserService
import com.example.cocktail_dakk.ui.start.StartActivity
import com.google.gson.Gson
import java.util.*

class SplashActivity : AppCompatActivity(), AutoLoginView {
    lateinit var binding: ActivitySplashBinding
    lateinit var instantid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            var spf = getSharedPreferences("InstanceID", AppCompatActivity.MODE_PRIVATE)
            if (spf.getString("InstanceID"," ") == " "){
                var editor: SharedPreferences.Editor = spf?.edit()!!
                editor.putString("InstanceID", UUID.randomUUID().toString())
                editor.commit()
            }
            instantid = spf!!.getString("InstanceID"," ")!!
            val autologinservce= UserService()
            autologinservce.setautologinView(this)
            autologinservce.autologin(instantid)
        }, 1000)
    }

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
        val intent = Intent(this, StartActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
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
            autologinbody.age, autologinbody.alcoholLevel, instantid,
            autologinbody.nickname, autologinbody.sex, gijulist, keywrodlist
        )
        val gson = Gson()
        var spf = getSharedPreferences("UserInfo", MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putString("UserInfo", gson.toJson(userinfo))
        editor.apply()
    }

}