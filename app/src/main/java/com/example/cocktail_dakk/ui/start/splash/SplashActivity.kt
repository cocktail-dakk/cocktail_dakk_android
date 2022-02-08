package com.example.cocktail_dakk.ui.start.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktail_dakk.databinding.ActivitySplashBinding
import com.example.cocktail_dakk.ui.main.MainActivity
import com.example.cocktail_dakk.ui.start.Service.AutoLoginView
import com.example.cocktail_dakk.ui.start.Service.Autologinbody
import com.example.cocktail_dakk.ui.start.Service.UserService
import com.example.cocktail_dakk.ui.start.StartActivity
import java.util.*

class SplashActivity : AppCompatActivity(), AutoLoginView {
    lateinit var binding: ActivitySplashBinding

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

            val autologinservce= UserService()
            autologinservce.setautologinView(this)
//            Log.d("autologin id",spf!!.getString("InstanceID"," ")!!)
            autologinservce.autologin(spf!!.getString("InstanceID"," ")!!)


        }, 1000)
    }

    override fun onLoginLoading() {

    }

    override fun onLoginSuccess(autologinbody: Autologinbody) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent) // 'S'tartActivity 시작
    }

    override fun onLoginFailure(code: Int, message: String) {
        val intent = Intent(this, StartActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}