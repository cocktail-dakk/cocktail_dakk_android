package com.umcapplunching.cocktail_dakk.ui.locker

import com.umcapplunching.cocktail_dakk.databinding.ActivitySettingsBinding
import com.umcapplunching.cocktail_dakk.ui.BaseActivity
import com.umcapplunching.cocktail_dakk.utils.gso

class SettingsActivity : BaseActivity<ActivitySettingsBinding>(ActivitySettingsBinding::inflate) {

    override fun initAfterBinding() {

        binding.settingsBackIv.setOnClickListener(){
            finish()
        }

    }

}