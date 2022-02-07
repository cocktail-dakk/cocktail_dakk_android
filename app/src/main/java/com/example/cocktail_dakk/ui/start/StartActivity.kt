package com.example.cocktail_dakk.ui.start

import com.example.cocktail_dakk.databinding.ActivityStartBinding
import com.example.cocktail_dakk.ui.BaseActivity
import com.example.cocktail_dakk.ui.main.MainActivity
import com.example.cocktail_dakk.ui.start.setting.StartNameActivity
import com.example.cocktail_dakk.ui.start.setting.StartSettingActivity

class StartActivity : BaseActivity<ActivityStartBinding>(ActivityStartBinding::inflate) {

    override fun initAfterBinding() {
        binding.startStartBtnTv.setOnClickListener(){
            startActivityWithClear(StartNameActivity::class.java)
        }

        binding.startSkipBtnIv.setOnClickListener(){
            startActivityWithClear(MainActivity::class.java)
        }

        binding.startSkipTv.setOnClickListener(){
            startActivityWithClear(MainActivity::class.java)
        }
    }

}