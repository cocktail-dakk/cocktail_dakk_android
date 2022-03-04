package com.umcapplunching.cocktail_dakk.ui.main.home_detail
import com.umcapplunching.cocktail_dakk.databinding.ActivityHomeDetailBinding
import com.umcapplunching.cocktail_dakk.ui.BaseActivity
import com.umcapplunching.cocktail_dakk.ui.BaseFragment

class HomeDetailActivity : BaseActivity<ActivityHomeDetailBinding>(ActivityHomeDetailBinding::inflate) {
    override fun initAfterBinding() {
        binding.homeDetailBackIv.setOnClickListener {
            finish()
        }
    }
}