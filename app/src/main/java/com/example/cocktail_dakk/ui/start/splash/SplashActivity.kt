package com.example.cocktail_dakk.ui.start.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktail_dakk.databinding.ActivitySplashBinding
import com.example.cocktail_dakk.ui.start.StartActivity
import java.util.*

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({

//            Log.d("uid테스트",UUID.randomUUID().toString()) //guid
            var spf = getSharedPreferences("InstanceID", AppCompatActivity.MODE_PRIVATE)
            if (spf.getString("InstanceID"," ") == " "){
                var editor: SharedPreferences.Editor = spf?.edit()!!
                editor.putString("InstanceID", UUID.randomUUID().toString())
                editor.commit()
            }else{

            }
//            Log.d("InstanceId",spf.getString("InstanceID", " ").toString())
            startActivity(Intent(this, StartActivity::class.java)) // 'S'tartActivity 시작
            finish()


        }, 1000)
    }

}