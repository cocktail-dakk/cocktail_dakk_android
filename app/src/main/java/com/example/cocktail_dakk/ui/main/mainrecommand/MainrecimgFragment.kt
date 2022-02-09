package com.example.cocktail_dakk.ui.main.mainrecommand

import android.content.Intent
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentMainrecimgBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.MainActivity
import com.example.cocktail_dakk.ui.main.MainFragment
import com.example.cocktail_dakk.ui.menu_detail.MenuDetailActivity


class MainrecimgFragment(var cocktailid : Int,val imgRes: String) : BaseFragment<FragmentMainrecimgBinding>(FragmentMainrecimgBinding::inflate) {
    override fun initAfterBinding() {
        Glide.with(this)
            .load(imgRes)
            .error(R.drawable.detail_star)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.mainRecimgIv)
        binding.mainRecimgIv.setOnClickListener{
//            val intent = Intent(activity, MenuDetailActivity::class.java)
//            intent.putExtra("id",cocktailid)
//            startActivity(intent)
              (activity as MainActivity).detailcocktail(cocktailid)
        }
    }
}