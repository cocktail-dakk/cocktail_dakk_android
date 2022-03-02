package com.umcapplunching.cocktail_dakk.ui.locker

import com.umcapplunching.cocktail_dakk.databinding.ActivitySettingPersonalPolicyBinding
import com.umcapplunching.cocktail_dakk.ui.BaseActivity

class SettingsPersonalActivity : BaseActivity<ActivitySettingPersonalPolicyBinding>(
    ActivitySettingPersonalPolicyBinding::inflate) {
    override fun initAfterBinding() {
        binding.settingsSettingPersonalBackIv.setOnClickListener(){
            finish()
        }
    }
}