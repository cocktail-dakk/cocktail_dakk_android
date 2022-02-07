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
import android.content.SharedPreferences

import android.view.Gravity

import android.widget.FrameLayout

import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    val information = arrayListOf("  맞춤 추천  ", "  키워드 추천  ")

    override fun initAfterBinding() {

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

        //exitionicon
        binding.mainSearchbarExiticonIv.setOnClickListener {
            var spf = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = spf?.edit()!!
            editor.putString("searchstr", " ")
            editor.apply()
            binding.mainSearchbarTv.setText("검색어를 입력해주세요.")
        }

        //검색뭐가 보이는지 설정
        var spf = activity?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
        var text = spf!!.getString("searchstr", " ")
        if (text == " ") {
            binding.mainSearchbarExiticonIv.visibility = View.GONE
            binding.mainSearchbarTv.setText("검색어를 입력해주세요.")
        } else {
            binding.mainSearchbarExiticonIv.visibility = View.VISIBLE
            binding.mainSearchbarTv.setText(text)
        }

        binding.mainTl.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0){
                    val metrics = resources.displayMetrics
                    val widthPixels = metrics.widthPixels
                    val heightPixels = metrics.heightPixels - 160 //작동 안함
                    val params = CoordinatorLayout.LayoutParams(widthPixels, heightPixels)
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
            var animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.horizon_out)
            animTransRight.duration = 700
            binding.mainSearchbarIv.startAnimation(animTransRight)
            startActivity(Intent(activity, SearchTabActivity::class.java))

        }
    }


//    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
//        super.startActivityForResult(intent, requestCode)
//        (activity as MainActivity).changeSearchtab()
//    }



//        var spf =  activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
//        var editor : SharedPreferences.Editor = spf?.edit()!!
//        editor.putInt("currenttab",binding.mainVp.currentItem)
//        editor.commit()


}   