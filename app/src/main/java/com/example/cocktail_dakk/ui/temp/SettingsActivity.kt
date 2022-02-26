package com.example.cocktail_dakk.ui.temp

import com.example.cocktail_dakk.databinding.ActivitySettingsBinding
import com.example.cocktail_dakk.ui.BaseActivity

class SettingsActivity : BaseActivity<ActivitySettingsBinding>(ActivitySettingsBinding::inflate) {

    override fun initAfterBinding() {

        binding.settingsBackIv.setOnClickListener(){
            finish()
        }
    }

}