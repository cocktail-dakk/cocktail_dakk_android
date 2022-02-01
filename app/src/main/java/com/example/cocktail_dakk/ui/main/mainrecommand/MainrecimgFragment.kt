package com.example.cocktail_dakk.ui.main.mainrecommand

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentMainrecimgBinding
import com.example.cocktail_dakk.ui.BaseFragment


class MainrecimgFragment(val imgRes: String) : BaseFragment<FragmentMainrecimgBinding>(FragmentMainrecimgBinding::inflate) {
    override fun initAfterBinding() {
        Glide.with(this)
            .load(imgRes)
            .error(R.drawable.detail_star)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.mainRecimgIv)
    }
}