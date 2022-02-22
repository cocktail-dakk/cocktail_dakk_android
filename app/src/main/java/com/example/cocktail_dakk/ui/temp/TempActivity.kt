package com.example.cocktail_dakk.ui.temp

import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.ActivityTempBinding
import com.example.cocktail_dakk.ui.BaseActivity
import com.example.cocktail_dakk.ui.search_tab.SearchTabBaseFragment


class TempActivity: BaseActivity<ActivityTempBinding>(ActivityTempBinding::inflate) {
    override fun initAfterBinding() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.temp_frame_la, LockerFragment())
            .commitAllowingStateLoss()
    }

}


