package com.example.cocktail_dakk.ui.main.home_detail
import com.example.cocktail_dakk.databinding.ActivityHomeDetailBinding
import com.example.cocktail_dakk.ui.BaseActivity
import com.example.cocktail_dakk.ui.BaseFragment

class HomeDetailActivity : BaseActivity<ActivityHomeDetailBinding>(ActivityHomeDetailBinding::inflate) {
    override fun initAfterBinding() {
        binding.homeDetailBackIv.setOnClickListener {
            finish()
        }
    }
}