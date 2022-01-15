//package com.cock_tail.test_xml.ui.main
package com.example.cocktail_dakk.ui.main

//import com.cock_tail.test_xml.databinding.FragmentMainBinding
//import com.cock_tail.test_xml.ui.BaseFragment
//import com.cock_tail.test_xml.ui.main.adapter.MainViewpagerAdapter
import android.content.Intent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentMainBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.adapter.MainViewpagerAdapter
import com.example.cocktail_dakk.ui.search_tab.SearchTabActivity
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    val information = arrayListOf("  맞춤 추천  ", "  키워드 추천  ")

    override fun initAfterBinding() {
        binding.mainSearchbarIv.visibility = View.VISIBLE
        binding.mainVp.isUserInputEnabled = false
        binding.mainVp.adapter = MainViewpagerAdapter(this)
        TabLayoutMediator(binding.mainTl, binding.mainVp) { tab, position ->
            tab.text = information[position]
        }.attach()
        binding.mainSearchbarIv.setOnClickListener{
            startActivity(Intent(activity, SearchTabActivity::class.java))
            var animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.horizon_out);
            binding.mainSearchbarIv.startAnimation(animTransRight)
            binding.mainSearchbarIv.visibility = View.INVISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
//        var animTransRight: Animation = AnimationUtils
//            .loadAnimation(activity, R.anim.horizon_out);
//        binding.mainSearchbarIv.startAnimation(animTransRight)
//        overridePendingTransition(R.anim.vertical_enter, R.anim.none)

    }
}