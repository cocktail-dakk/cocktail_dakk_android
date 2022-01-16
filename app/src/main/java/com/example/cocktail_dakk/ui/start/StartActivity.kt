package com.example.cocktail_dakk.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cocktail_dakk.R
import kotlinx.android.synthetic.main.activity_start.*


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        start_start_btn_tv.setOnClickListener {
            onStartButtonClicked()
        }

    }

    fun onStartButtonClicked(){

        val intent = Intent(this, StartNameActivity::class.java)

        startActivity(intent)

    }

}