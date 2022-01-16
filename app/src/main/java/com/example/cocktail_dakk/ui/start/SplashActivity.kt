package com.example.cocktail_dakk.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val intent = Intent(this, StartActivity::class.java)  // 메인 액티비티로 화면 넘어감
        startActivity(intent)
        finish()
    }
}