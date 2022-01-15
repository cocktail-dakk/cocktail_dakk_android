package com.example.cocktail_dakk.ui.search_tab

import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.ActivitySearchTabBinding
import com.example.cocktail_dakk.ui.BaseActivity

class SearchTabActivity : BaseActivity<ActivitySearchTabBinding>(ActivitySearchTabBinding::inflate) {

    override fun initAfterBinding() {
        supportFragmentManager.beginTransaction().replace(R.id.search_tab_frame_la, SearchResultFragment())
            .commitAllowingStateLoss()

    }

}