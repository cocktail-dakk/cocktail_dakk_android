package com.example.cocktail_dakk.ui.main

import android.R.attr
import android.content.Intent
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ScrollView
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentMainBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.adapter.MainViewpagerAdapter
import com.example.cocktail_dakk.ui.search_tab.SearchTabActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import android.R.attr.popupLayout
import android.app.ActionBar

import android.view.Gravity

import android.widget.FrameLayout

import android.util.DisplayMetrics
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    val information = arrayListOf("  맞춤 추천  ", "  키워드 추천  ")

    override fun initAfterBinding() {

//        val tabspf =  activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)  //spf 받아오기
//        val currenttab = tabspf!!.getInt("currenttab",0)


        val metrics = resources.displayMetrics
        val widthPixels = metrics.widthPixels
        val heightPixels = metrics.heightPixels - 160
        val params = CoordinatorLayout.LayoutParams(widthPixels,heightPixels)

        binding.mainVp.layoutParams = params

        binding.mainVp.isUserInputEnabled = false
        binding.mainVp.adapter = MainViewpagerAdapter(this)
        TabLayoutMediator(binding.mainTl, binding.mainVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        binding.mainTl.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("main_test",tab?.position.toString())
                if (tab?.position == 0){
                    val metrics = resources.displayMetrics
                    val widthPixels = metrics.widthPixels
                    val heightPixels = metrics.heightPixels - 160
                    val params = CoordinatorLayout.LayoutParams(widthPixels,heightPixels)
                    binding.mainVp.layoutParams = params
                }
                else{
                    val metrics = resources.displayMetrics
                    val widthPixels = metrics.widthPixels
                    val params = CoordinatorLayout.LayoutParams(widthPixels,ActionBar.LayoutParams.MATCH_PARENT)
                    var behavior : AppBarLayout.ScrollingViewBehavior
                    behavior = AppBarLayout.ScrollingViewBehavior()
                    params.behavior = behavior
                    binding.mainVp.layoutParams = params
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })



        binding.mainSearchbarIv.setOnClickListener{
            startActivity(Intent(activity, SearchTabActivity::class.java))
            var animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.horizon_out);
            binding.mainSearchbarIv.startAnimation(animTransRight)

        //            binding.mainSearchbarIv.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
//        binding.mainSearchbarIv.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
//
//        var spf =  activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
//        var editor : SharedPreferences.Editor = spf?.edit()!!
//        editor.putInt("currenttab",binding.mainVp.currentItem)
//        editor.commit()
    }

}