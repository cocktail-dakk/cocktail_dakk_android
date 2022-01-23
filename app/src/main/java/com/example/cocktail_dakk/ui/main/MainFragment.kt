//package com.cock_tail.test_xml.ui.main
package com.example.cocktail_dakk.ui.main

//import com.cock_tail.test_xml.databinding.FragmentMainBinding
//import com.cock_tail.test_xml.ui.BaseFragment
//import com.cock_tail.test_xml.ui.main.adapter.MainViewpagerAdapter
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
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

        val tabspf =  activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)  //spf 받아오기
        val currenttab = tabspf!!.getInt("currenttab",0)
        //binding.mainVp.setCurrentItem(currenttab)

        binding.mainVp.isUserInputEnabled = false
        binding.mainVp.adapter = MainViewpagerAdapter(this)
        TabLayoutMediator(binding.mainTl, binding.mainVp) { tab, position ->
            tab.text = information[position]
        }.attach()
        //binding.mainVp.setCurrentItem(currenttab)


        binding.mainSearchbarIv.setOnClickListener{
            startActivity(Intent(activity, SearchTabActivity::class.java))
            var animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.horizon_out);
            binding.mainSearchbarIv.startAnimation(animTransRight)
            binding.mainSearchbarIv.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mainSearchbarIv.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()

        var spf =  activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab",binding.mainVp.currentItem)
        editor.commit()
    }

}