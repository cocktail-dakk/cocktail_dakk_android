package com.umcapplunching.cocktail_dakk.ui.main.mainrecommand

import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.FragmentMainrecimgBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.menu_detail.MenuDetailActivity

class MainrecimgFragment(var cocktailid : Int,val imgRes: String)
    : BaseFragment<FragmentMainrecimgBinding>(FragmentMainrecimgBinding::inflate) {
    override fun initAfterBinding() {
        Glide.with(this)
            .load(imgRes)
            .error(R.drawable.detail_star)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.mainRecimgIv)
        binding.mainRecimgIv.setOnClickListener{
//              (activity as MainActivity).detailcocktail(cocktailid)
            val intent = Intent(requireContext(), MenuDetailActivity::class.java)
            intent.putExtra("cocktailId",cocktailid)
            startActivity(intent)
        }
    }
}