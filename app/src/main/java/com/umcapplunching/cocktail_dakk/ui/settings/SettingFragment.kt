package com.umcapplunching.cocktail_dakk.ui.settings

import android.util.Log
import com.umcapplunching.cocktail_dakk.databinding.FragmentSettingsBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity

class SettingFragment: BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    override fun initAfterBinding() {
        binding.settingsBackIv.setOnClickListener {
//            R.id.mypage_setting_framelayout.visibility
            (activity as MainActivity).DetailBackArrow()
        }

    }
}