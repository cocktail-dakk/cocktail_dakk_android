package com.example.cocktail_dakk.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cocktail_dakk.R
import kotlinx.android.synthetic.main.activity_start_name.*


class StartNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_name)

        start_start_btn_tv.setOnClickListener {
            onStartNameButtonClicked()
        }

    }

    fun onStartNameButtonClicked(){

        val intent = Intent(this, StartSettingActivity::class.java)

        startActivity(intent)

    }

}