package com.example.cocktail_dakk.ui.search_tab

import android.os.Bundle
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.ActivitySearchTabBinding
import com.example.cocktail_dakk.ui.BaseActivity

class SearchTabActivity : BaseActivity<ActivitySearchTabBinding>(ActivitySearchTabBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.vertical_enter,R.anim.none)
    }

    override fun initAfterBinding() {
        supportFragmentManager.beginTransaction().replace(R.id.search_tab_frame_la, SearchResultFragment())
            .commitAllowingStateLoss()

        binding.searchTabBackIv.setOnClickListener {
            finish()
        }
    }

}