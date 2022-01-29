//package com.cock_tail.test_xml.ui.main.mrecommand
package com.example.cocktail_dakk.ui.main.mainrecommand

import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentMainrecimgBinding
import com.example.cocktail_dakk.ui.BaseFragment

//import com.cock_tail.test_xml.databinding.FragmentMainrecimgBinding
//import com.cock_tail.test_xml.ui.BaseFragment

class MainrecimgFragment(val imgRes: String) : BaseFragment<FragmentMainrecimgBinding>(FragmentMainrecimgBinding::inflate) {
    override fun initAfterBinding() {
//        binding.mainRecimgIv.setImageResource(imgRes)

        Glide.with(this)
            .load(imgRes)
            .error(R.drawable.detail_star)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.mainRecimgIv)
    }
}